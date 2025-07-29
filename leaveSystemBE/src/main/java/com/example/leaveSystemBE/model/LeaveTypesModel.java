package com.example.leaveSystemBE.model;

public class LeaveTypesModel {

    private int id;
    private String name;
    private String description;
    private int maxDays;

    public LeaveTypesModel() {
        // constructor
    }

    public LeaveTypesModel(int id, String name, String description, int maxDays) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxDays = maxDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }
}
