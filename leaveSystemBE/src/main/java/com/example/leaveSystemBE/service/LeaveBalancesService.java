package com.example.leaveSystemBE.service;

import com.example.leaveSystemBE.entity.LeaveBalancesEntity;
import com.example.leaveSystemBE.entity.UsersEntity;
import com.example.leaveSystemBE.model.LeaveBalancesModel;
import com.example.leaveSystemBE.repository.LeaveBalancesRepository;
import com.example.leaveSystemBE.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveBalancesService {

    @Autowired
    private LeaveBalancesRepository leaveBalancesRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<LeaveBalancesModel> getLeaveBalances(int userId) {
        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<LeaveBalancesEntity> entities = leaveBalancesRepository.findByUsersEntity(user);
        return entities.stream()
                .map(entity -> new LeaveBalancesModel(
                        entity.getId(),
                        entity.getUsersEntity().getId(),
                        entity.getLeaveTypesEntity().getId(),
                        entity.getYear(),
                        entity.getRemainingDays()))
                .toList();
    }
}
