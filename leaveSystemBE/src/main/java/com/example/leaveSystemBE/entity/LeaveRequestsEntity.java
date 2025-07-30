package com.example.leaveSystemBE.entity;

import com.example.leaveSystemBE.enumeration.LeaveStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "leave_requests")
public class LeaveRequestsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    //foreign key form Users
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity usersEntity;

    //foregin key form LeaveType
    @ManyToOne
    @JoinColumn(name = "leave_type_id", referencedColumnName = "id", nullable = false)
    private LeaveTypesEntity leaveTypesEntity;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private  Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , length = 20)
    private LeaveStatus status;

    @Column(name = "reason")
    private  String reason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "approval_note")
    private String approvalNote;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovalNote() {
        return approvalNote;
    }

    public void setApprovalNote(String approvalNote) {
        this.approvalNote = approvalNote;
    }


}
