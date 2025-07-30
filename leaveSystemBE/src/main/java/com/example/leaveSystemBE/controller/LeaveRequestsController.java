package com.example.leaveSystemBE.controller;

import com.example.leaveSystemBE.entity.LeaveRequestsEntity;
import com.example.leaveSystemBE.model.LeaveRequestsModel;
import com.example.leaveSystemBE.service.LeaveRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestsController {

    @Autowired
    private LeaveRequestsService leaveRequestsService;

    @PostMapping
    public ResponseEntity<LeaveRequestsEntity> createLeaveRequest(@RequestBody LeaveRequestsModel model) {
        LeaveRequestsEntity saved = leaveRequestsService.createLeaveRequest(model);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestsModel>> getLeaveRequests(@RequestParam(required = false) Integer userId) {
        List<LeaveRequestsModel> requests = leaveRequestsService.getLeaveRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestsEntity> updateStatus(@PathVariable int id,   @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        String approvalNote = payload.get("approvalNote");

        LeaveRequestsEntity updated = leaveRequestsService.updateStatus(id, status, approvalNote);
        return ResponseEntity.ok(updated);
    }
}
