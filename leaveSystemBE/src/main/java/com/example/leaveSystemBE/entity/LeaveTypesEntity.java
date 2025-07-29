package com.example.leaveSystemBE.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_types")
public class LeaveTypesEntity {

    @Id
    @Column(name = "id",unique = true, nullable = false)
    private int id;

    @Column(name = "name",length = 50)
    private  String name;

    @Column(name = "description")
    private String description;

    @Column(name = "max_days")
    private int maxDays;

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
