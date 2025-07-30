import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface LeaveRequest {
  id: number;
  username: string;
  department: string;
  leaveTypeId: number;
  startDate: string;
  endDate: string;
  totalDays: number;
  reason: string;
  status: string;
  approvalNote: string;
}

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrl: './manager.component.css'
})
export class ManagerComponent {


  leaveRequests: LeaveRequest[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchLeaveRequests();
  }


  getLeaveTypeName(typeId: number): string {
    const types: { [key: number]: string } = {
      1: 'ลาพักร้อน',
      2: 'ลาป่วย',
      3: 'ลากิจ'
    };
    return types[typeId] || 'ไม่ระบุ';
  }

  calculateTotalDays(start: string, end: string): number {
    const startDate = new Date(start);
    const endDate = new Date(end);
    const timeDiff = endDate.getTime() - startDate.getTime();
    return Math.floor(timeDiff / (1000 * 3600 * 24)) + 1; // รวมวันเริ่ม
  }


  fetchLeaveRequests() {
    this.http.get<LeaveRequest[]>('http://localhost:8080/api/leave-requests')
      .subscribe(data => {

        this.leaveRequests = data.filter(req => req.status === 'PENDING');
        this.leaveRequests.forEach(req => {
          req.status = 'รออนุมัติ';
          (req as any).leaveTypeName = this.getLeaveTypeName(req.leaveTypeId);
          req.totalDays = this.calculateTotalDays(req.startDate, req.endDate);
        });
      });
  }



  approveLeave(index: number) {
    const leave = this.leaveRequests[index];
    const body = {
      status: 'APPROVED',
      approvalNote: leave.approvalNote || ''  // เอาค่าจาก textarea
    };

    this.http.put(`http://localhost:8080/api/leave-requests/${leave.id}`, body)
      .subscribe(() => {
        this.leaveRequests[index].status = 'อนุมัติ';
        this.leaveRequests.splice(index, 1);
        alert('ดำเนินการอนุมัติเรียบร้อยแล้ว');
      }, error => {
        console.error('Error approving leave:', error);
      });
  }

  rejectLeave(index: number) {
    const leave = this.leaveRequests[index];
    const body = {
      status: 'REJECTED',
      approvalNote: leave.approvalNote || ''
    };

    this.http.put(`http://localhost:8080/api/leave-requests/${leave.id}`, body)
      .subscribe(() => {
        this.leaveRequests[index].status = 'ไม่อนุมัติ';
        this.leaveRequests.splice(index, 1);
        alert('ดำเนินการไม่อนุมัติเรียบร้อยแล้ว');
      }, error => {
        console.error('Error rejecting leave:', error);
      });
  }


}
