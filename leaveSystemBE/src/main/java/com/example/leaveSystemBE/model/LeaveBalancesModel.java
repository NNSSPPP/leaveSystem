package com.example.leaveSystemBE.model;

public class LeaveBalancesModel {

    private int id;
    private int userId;
    private int leaveTypeId;
    private int year;
    private int remainingDays;

    public LeaveBalancesModel() {
        // constructor
    }

    public LeaveBalancesModel(int id, int userId, int leaveTypeId, int year, int remainingDays) {
        this.id = id;
        this.userId = userId;
        this.leaveTypeId = leaveTypeId;
        this.year = year;
        this.remainingDays = remainingDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }
}
