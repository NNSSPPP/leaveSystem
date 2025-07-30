import { Component} from '@angular/core';
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
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  usedDays = 0;
  pending = 0;
  totalDays = 47;

  recentLeaves: any[] = [];
  leaveRequests: LeaveRequest[] = [];
  Balances: any[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getDashboardSummary();
    this.getRecentLeaves();
    this.countPending();
  }

  getDashboardSummary(): void {
    this.http.get<any[]>('http://localhost:8080/api/leave-balances?userId=1').subscribe({
      next: (data) => {
        this.Balances = data;
      },
      error: (err) => {
        console.error('Error fetching dashboard summary', err);
      }
    });
  }

  sumUsedDays(): number {
    return this.Balances.reduce((sum, b) => sum + b.usedDays, 0);
  }

  countPending() {
    this.http.get<LeaveRequest[]>('http://localhost:8080/api/leave-requests')
      .subscribe(data => {
        this.leaveRequests = data;
        this.pending = this.leaveRequests.filter(req => req.status === 'PENDING').length;
      }, error => {
        console.error('Error fetching leave requests', error);
      });
  }

  getRecentLeaves(): void {
    this.http.get<any[]>('http://localhost:8080/api/leave-requests?userId=1').subscribe({
      next: (data) => {
        this.recentLeaves = data;
        this.recentLeaves = data.sort((a, b) => {
          const dateA = new Date(a.startDate).getTime();
          const dateB = new Date(b.startDate).getTime();
          return dateB - dateA;
        });
      },
      error: (err) => {
        console.error('Error fetching recent leaves', err);
      }
    });
  }

  getLeaveTypeName(typeId: number): string {
    const types: { [key: number]: string } = {
      1: 'ลาพักร้อน',
      2: 'ลาป่วย',
      3: 'ลากิจ'
    };
    return types[typeId] || 'ไม่ระบุ';
  }

  calculateLeaveDays(start: string, end: string): number {
    const startDate = new Date(start);
    const endDate = new Date(end);
    const diffTime = Math.abs(endDate.getTime() - startDate.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1; 
    return diffDays;
  }

  formatLeaveDateRange(start: string, end: string): string {
    const thaiMonths = [
      'ม.ค.', 'ก.พ.', 'มี.ค.', 'เม.ย.', 'พ.ค.', 'มิ.ย.',
      'ก.ค.', 'ส.ค.', 'ก.ย.', 'ต.ค.', 'พ.ย.', 'ธ.ค.'
    ];

    const startDate = new Date(start);
    const endDate = new Date(end);

    const startDay = startDate.getDate();
    const startMonth = startDate.getMonth();
    const startYear = startDate.getFullYear() + 543; 

    const endDay = endDate.getDate();
    const endMonth = endDate.getMonth();
    const endYear = endDate.getFullYear() + 543;

    if (startYear !== endYear) {
      return `${startDay} ${thaiMonths[startMonth]} ${startYear} - ${endDay} ${thaiMonths[endMonth]} ${endYear}`;
    }

    if (startMonth === endMonth) {
      if (startDay === endDay) {
        return `${startDay} ${thaiMonths[startMonth]} ${startYear}`;
      } else {
        return `${startDay}-${endDay} ${thaiMonths[startMonth]} ${startYear}`;
      }
    } else {
      return `${startDay} ${thaiMonths[startMonth]} - ${endDay} ${thaiMonths[endMonth]} ${startYear}`;
    }
  }

  getStatusThai(status: string): string {
    const statusMap: { [key: string]: string } = {
      'APPROVED': 'อนุมัติ',
      'REJECTED': 'ไม่อนุมัติ',
      'PENDING': 'รออนุมัติ'
    };
    return statusMap[status] || status;
  }
}
