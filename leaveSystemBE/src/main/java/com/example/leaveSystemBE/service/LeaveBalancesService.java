package com.example.leaveSystemBE.service;

import com.example.leaveSystemBE.entity.LeaveBalancesEntity;
import com.example.leaveSystemBE.entity.LeaveRequestsEntity;
import com.example.leaveSystemBE.entity.LeaveTypesEntity;
import com.example.leaveSystemBE.entity.UsersEntity;
import com.example.leaveSystemBE.enumeration.LeaveStatus;
import com.example.leaveSystemBE.model.LeaveBalancesModel;
import com.example.leaveSystemBE.repository.LeaveBalancesRepository;
import com.example.leaveSystemBE.repository.LeaveRequestsRepository;
import com.example.leaveSystemBE.repository.LeaveTypesRepository;
import com.example.leaveSystemBE.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LeaveBalancesService {

    @Autowired
    private LeaveBalancesRepository leaveBalancesRepository;

    @Autowired
    private LeaveRequestsRepository leaveRequestsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LeaveTypesRepository leaveTypesRepository;



    public List<LeaveBalancesModel> getLeaveBalances(int userId) {
        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<LeaveBalancesEntity> balances = leaveBalancesRepository.findByUsersEntity(user);

        return balances.stream()
                .map(balance -> {
                    int leaveTypeId = balance.getLeaveTypesEntity().getId();
                    int totalDays = balance.getLeaveTypesEntity().getMaxDays();

                    List<LeaveRequestsEntity> approvedLeaves = leaveRequestsRepository
                            .findByUsersEntityAndLeaveTypesEntityAndStatus(user, balance.getLeaveTypesEntity(), LeaveStatus.APPROVED);

                    int usedDays = approvedLeaves.stream()
                            .mapToInt(req -> calculateLeaveDays(req.getStartDate(), req.getEndDate()))
                            .sum();

                    int remaining = totalDays - usedDays;

                    return new LeaveBalancesModel(
                            balance.getId(),
                            userId,
                            leaveTypeId,
                            balance.getYear(),
                            totalDays,
                            usedDays,
                            remaining
                    );
                })
                .toList();
    }

    private int calculateLeaveDays(Date startDate, Date endDate) {
        long millisPerDay = 1000 * 60 * 60 * 24;
        long diff = (endDate.getTime() - startDate.getTime()) / millisPerDay;
        return (int) diff + 1; // +1 เพื่อรวมวันแรกและวันสุดท้าย
    }





    public void calBalanceIfApproved(LeaveRequestsEntity leaveRequestsEntity) {
        Date startDate = leaveRequestsEntity.getStartDate();
        Date endDate = leaveRequestsEntity.getEndDate();

        if (startDate == null || endDate == null) return;

        int days = (int) TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime()) + 1;

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int year = cal.get(Calendar.YEAR);

        LeaveTypesEntity type = leaveTypesRepository.findById(leaveRequestsEntity.getLeaveTypesEntity().getId())
                .orElseThrow(() -> new RuntimeException("ไม่พบประเภทการลา"));

        UsersEntity user = usersRepository.findById(leaveRequestsEntity.getUsersEntity().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LeaveBalancesEntity balances = leaveBalancesRepository
                .findByUsersEntityAndLeaveTypesEntityAndYear(user, type, year);

        if (balances == null) {
            balances = new LeaveBalancesEntity();
            balances.setUsersEntity(user);
            balances.setLeaveTypesEntity(type);
            balances.setYear(year);
            balances.setRemainingDays(type.getMaxDays());
        }

        int newRemain = balances.getRemainingDays() - days;
        balances.setRemainingDays(Math.max(0, newRemain));

        leaveBalancesRepository.save(balances);
    }

    public List<LeaveBalancesModel> getAllBalance() {
        List<LeaveBalancesEntity> entities = leaveBalancesRepository.findAll();

        return entities.stream().map(entity -> {
            LeaveBalancesModel model = new LeaveBalancesModel();
            model.setId(entity.getId());
            model.setUserId(entity.getUsersEntity().getId());
            model.setLeaveTypeId(entity.getLeaveTypesEntity().getId());
            model.setYear(entity.getYear());
            model.setRemainingDays(entity.getRemainingDays());
            model.setLeaveTypeId(entity.getLeaveTypesEntity() != null ? entity.getLeaveTypesEntity().getId() : 0);

            return model;
        }).collect(Collectors.toList());
    }
}
