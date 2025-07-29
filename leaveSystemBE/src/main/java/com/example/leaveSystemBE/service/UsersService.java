package com.example.leaveSystemBE.service;

import com.example.leaveSystemBE.entity.UsersEntity;
import com.example.leaveSystemBE.model.UsersModel;
import com.example.leaveSystemBE.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public void saveUser(UsersModel usersModel) {

        UsersEntity usersEntity = new UsersEntity();

        usersEntity.setId(usersModel.getId());
        usersEntity.setUsername(usersModel.getUsername());
        usersEntity.setEmail(usersModel.getEmail());
        usersEntity.setRole(usersModel.getRole());
        usersEntity.setDepartment(usersModel.getDepartment());
        if(usersModel.getCreatedDate() == null) {
        usersEntity.setCreatedDate(new Date());
        }

        usersRepository.save(usersEntity);

    }
}
