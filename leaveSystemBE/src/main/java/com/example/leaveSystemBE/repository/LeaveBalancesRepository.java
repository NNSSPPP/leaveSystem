package com.example.leaveSystemBE.repository;

import com.example.leaveSystemBE.entity.LeaveBalancesEntity;
import com.example.leaveSystemBE.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalancesRepository extends JpaRepository<LeaveBalancesEntity,Integer> {
    List<LeaveBalancesEntity> findByUsersEntity(UsersEntity usersEntity);
}
