package com.example.leaveSystemBE.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balances")
public class LeaveBalancesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //foreign key form Users
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity usersEntity;

    //foregin key form LeaveType
    @ManyToOne
    @JoinColumn(name = "leave_type_id", referencedColumnName = "id", nullable = false)
    private LeaveTypesEntity leaveTypesEntity;

    @Column(name = "year")
    private  int year;

    @Column(name = "remaining_days")
    private  int remainingDays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    public LeaveTypesEntity getLeaveTypesEntity() {
        return leaveTypesEntity;
    }

    public void setLeaveTypesEntity(LeaveTypesEntity leaveTypesEntity) {
        this.leaveTypesEntity = leaveTypesEntity;
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
