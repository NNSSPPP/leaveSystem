package com.example.leaveSystemBE.repository;

import com.example.leaveSystemBE.entity.LeaveRequestsEntity;
import com.example.leaveSystemBE.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestsRepository extends JpaRepository<LeaveRequestsEntity,Integer> {
    List<LeaveRequestsEntity> findByUsersEntity(UsersEntity usersEntity);
}
