package com.example.leaveSystemBE.controller;

import com.example.leaveSystemBE.model.UsersModel;
import com.example.leaveSystemBE.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/saveuser")
    public void saveUser (@RequestBody UsersModel usersModel) {
        usersService.saveUser(usersModel);
    }
}
