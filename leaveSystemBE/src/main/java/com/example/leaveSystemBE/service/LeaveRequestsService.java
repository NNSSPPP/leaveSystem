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

    public LeaveRequestsEntity createLeaveRequest(LeaveRequestsModel model) {
        LeaveRequestsEntity entity = new LeaveRequestsEntity();
        entity.setUsersEntity(usersRepository.findById(model.getUserId()).orElseThrow());
        entity.setLeaveTypesEntity(leaveTypesRepository.findById(model.getLeaveTypeId()).orElseThrow());
        entity.setReason(model.getReason());
        entity.setStartDate(model.getStartDate());
        entity.setEndDate(model.getEndDate());
        entity.setStatus(LeaveStatus.PENDING);
        entity.setCreatedDate(new Date());
        return leaveRequestsRepository.save(entity);
    }

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
            model.setStatus(e.getStatus().name()); // ส่งกลับเป็น String
            return model;
        }).toList();
    }

    public LeaveRequestsEntity updateStatus(int id, String status) {
        LeaveRequestsEntity entity = leaveRequestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบคำขอลา ID: " + id));

        try {
            LeaveStatus newStatus = LeaveStatus.valueOf(status.toUpperCase());
            entity.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("สถานะไม่ถูกต้อง: " + status);
        }

        return leaveRequestsRepository.save(entity);
    }
}
