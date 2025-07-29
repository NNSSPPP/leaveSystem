package com.example.leaveSystemBE.repository;

import com.example.leaveSystemBE.entity.LeaveTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypesRepository extends JpaRepository <LeaveTypesEntity,Integer> {
}
