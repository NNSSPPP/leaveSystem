package com.example.leaveSystemBE.controller;

import com.example.leaveSystemBE.model.LeaveBalancesModel;
import com.example.leaveSystemBE.service.LeaveBalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalancesService leaveBalancesService;

    @GetMapping("/leave-balances")
    public ResponseEntity<List<LeaveBalancesModel>> getLeaveBalances(@RequestParam int userId) {
        List<LeaveBalancesModel> balances = leaveBalancesService.getLeaveBalances(userId);
        return ResponseEntity.ok(balances);
    }

}
