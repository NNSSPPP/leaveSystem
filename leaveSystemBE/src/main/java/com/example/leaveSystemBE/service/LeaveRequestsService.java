package com.example.leaveSystemBE.service;

import com.example.leaveSystemBE.entity.LeaveRequestsEntity;
import com.example.leaveSystemBE.entity.UsersEntity;
import com.example.leaveSystemBE.enumeration.LeaveStatus;
import com.example.leaveSystemBE.model.LeaveRequestsModel;
import com.example.leaveSystemBE.repository.LeaveRequestsRepository;
import com.example.leaveSystemBE.repository.LeaveTypesRepository;
import com.example.leaveSystemBE.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeaveRequestsService {

    @Autowired
    private LeaveRequestsRepository leaveRequestsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LeaveTypesRepository  leaveTypesRepository;

    @Autowired
    private LeaveBalancesService leaveBalancesService;

    //save leaverequests
    public LeaveRequestsEntity createLeaveRequest(LeaveRequestsModel model) {
        try {
            LeaveRequestsEntity entity = new LeaveRequestsEntity();
            entity.setUsersEntity(usersRepository.findById(model.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
            entity.setLeaveTypesEntity(leaveTypesRepository.findById(model.getLeaveTypeId()).orElseThrow(() -> new RuntimeException("LeaveType not found")));
            entity.setReason(model.getReason());
            entity.setStartDate(model.getStartDate());
            entity.setEndDate(model.getEndDate());
            entity.setStatus(LeaveStatus.PENDING);
            entity.setCreatedDate(new Date());
            return leaveRequestsRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //search each user leaverequest
    public List<LeaveRequestsModel> getLeaveRequests(Integer userId) {
        List<LeaveRequestsEntity> entities;
        if (userId == null) {
            entities = leaveRequestsRepository.findAll();
        } else {
            UsersEntity user = usersRepository.findById(userId).orElseThrow(
                    () -> new RuntimeException("ไม่พบผู้ใช้ ID: " + userId));
            entities = leaveRequestsRepository.findByUsersEntity(user);
        }

        return entities.stream().map(e -> {
            LeaveRequestsModel model = new LeaveRequestsModel();
            model.setId(e.getId());
            model.setUserId(e.getUsersEntity().getId());
            model.setLeaveTypeId(e.getLeaveTypesEntity().getId());
            model.setReason(e.getReason());
            model.setStartDate(e.getStartDate());
            model.setEndDate(e.getEndDate());
            model.setStatus(e.getStatus().name());
            model.setCreatedDate(e.getCreatedDate());
            model.setUsername(e.getUsersEntity().getUsername());
            model.setDepartment(e.getUsersEntity().getDepartment());
            return model;
        }).toList();
    }

    //update status
    public LeaveRequestsEntity updateStatus(int id, String status,String approvalNote) {
        LeaveRequestsEntity entity = leaveRequestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบคำขอลา ID: " + id));
        LeaveStatus newStatus;

        try {
            newStatus = LeaveStatus.valueOf(status.toUpperCase());
            entity.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("สถานะไม่ถูกต้อง: " + status);
        }

        entity.setApprovalNote(approvalNote);

        LeaveRequestsEntity savedEntity = leaveRequestsRepository.save(entity);

        if (newStatus == LeaveStatus.APPROVED) {
            leaveBalancesService.calBalanceIfApproved(savedEntity);
        }

        return savedEntity;
    }
}
