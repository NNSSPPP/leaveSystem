package com.example.leaveSystemBE.controller;

import com.example.leaveSystemBE.entity.LeaveRequestsEntity;
import com.example.leaveSystemBE.model.LeaveBalancesModel;
import com.example.leaveSystemBE.service.LeaveBalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-balances")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalancesService leaveBalancesService;

    @GetMapping
    public ResponseEntity<List<LeaveBalancesModel>> getLeaveBalances(@RequestParam int userId) {
        List<LeaveBalancesModel> balances = leaveBalancesService.getLeaveBalances(userId);
        return ResponseEntity.ok(balances);
    }

    @PostMapping("/cal-balance")
    public ResponseEntity<Void> calculateBalanceIfApproved(@RequestBody LeaveRequestsEntity leaveRequest) {
        leaveBalancesService.calBalanceIfApproved(leaveRequest);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/findall")
    public ResponseEntity<List<LeaveBalancesModel>> getAllLeaveBalances() {
        List<LeaveBalancesModel> allBalances = leaveBalancesService.getAllBalance();
        return ResponseEntity.ok(allBalances);
    }

}
