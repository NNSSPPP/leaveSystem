import { Component} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import Chart from 'chart.js/auto';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver'; 

interface UserLeave {
  userId: number;
  username: string;
  department: string;
  leaveTypeId: number;
  startDate: string;
  endDate: string;
  status: string;
  sick : number;
  personal : number;
  vacation: number;
}

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
selectedLeaveTypeId: number | null = null;
  selectedStatus = '';
  status = ['Pending','Approved','Rejected'];
  
   summaryTable: any[] = [];
  filteredUsers: any[] = [];
  chart: any;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.callApi();
  }

  callApi() {
    this.http.get<UserLeave[]>('http://localhost:8080/api/leave-requests')
      .subscribe(data => {
        let filtered = data;

        if (this.selectedStatus) {
        filtered = filtered.filter(r => r.status.toLowerCase() === this.selectedStatus.toLowerCase());
      }

      if (this.selectedLeaveTypeId !== undefined && this.selectedLeaveTypeId !== null) {
        filtered = filtered.filter(r => r.leaveTypeId === this.selectedLeaveTypeId);
      }

        this.summaryTable = this.summarizeRequests(filtered);
        this.filteredUsers = this.summaryTable;
        this.renderChart();
      });
  }
  summarizeRequests(requests: UserLeave[]) {
    const summaryMap: { [userId: number]: any } = {};

    requests.forEach(req => {
      const days = this.calculateDays(req.startDate, req.endDate);
      if (!summaryMap[req.userId]) {
        summaryMap[req.userId] = {
          userId: req.userId,
          username: req.username,
          department: req.department,
          sick: 0,
          vacation: 0,
          personal: 0
        };
      }

      switch (req.leaveTypeId) {
        case 2: summaryMap[req.userId].sick += days; break;
        case 1: summaryMap[req.userId].vacation += days; break;
        case 3: summaryMap[req.userId].personal += days; break;
      }
    });

    return Object.values(summaryMap).map((entry: any) => ({
      ...entry,
      total: entry.sick + entry.vacation + entry.personal
    }));
  }

  getLeaveTypeName(typeId: number): string {
  const types: { [key: number]: string } = {
    1: 'ลาพักร้อน',
    2: 'ลาป่วย',
    3: 'ลากิจ'
  };
  return types[typeId] || 'ไม่ระบุ';
}

  calculateDays(start: string, end: string): number {
    const s = new Date(start);
    const e = new Date(end);
    const diff = Math.abs(e.getTime() - s.getTime());
    return Math.floor(diff / (1000 * 60 * 60 * 24)) + 1;
  }

  renderChart() {
  const sickSum = this.filteredUsers.reduce((sum, u) => sum + (u.sick || 0), 0);
  const personalSum = this.filteredUsers.reduce((sum, u) => sum + (u.personal || 0), 0);
  const vacationSum = this.filteredUsers.reduce((sum, u) => sum + (u.vacation || 0), 0);

  if (this.chart) {
    this.chart.destroy();
  }

  const ctx = (document.getElementById('leaveChart') as HTMLCanvasElement).getContext('2d');
  this.chart = new Chart(ctx!, {
    type: 'bar',
    data: {
      labels: ['ลาป่วย', 'ลาพักร้อน', 'ลากิจ'],  
      datasets: [{
        label: 'จำนวนวัน',
        data: [sickSum, vacationSum,personalSum],
        backgroundColor: 'rgba(54, 162, 235, 0.7)'
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: { beginAtZero: true }
      }
    }
  });
}

   exportExcel() {
  const exportData = this.filteredUsers.map(user => ({
    'ชื่อ-นามสกุล': user.username,
    'แผนก': user.department,
    'ลาป่วย': user.sick,
    'ลาพักร้อน': user.vacation,
    'ลากิจ': user.personal,
    'รวม': user.sick + user.vacation + user.personal
  }));

  const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(exportData);
  const workbook: XLSX.WorkBook = {
    Sheets: { 'Leave Summary': worksheet },
    SheetNames: ['Leave Summary']
  };

  const excelBuffer: any = XLSX.write(workbook, {
    bookType: 'xlsx',
    type: 'array'
  });

  const data: Blob = new Blob([excelBuffer], {
    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'
  });

  const leaveTypeName = this.selectedLeaveTypeId ? this.getLeaveTypeName(this.selectedLeaveTypeId) : 'AllTypesLeave';
  const status = this.selectedStatus || 'AllStatus';

  saveAs(data, `leave-summary-${leaveTypeName}-${status}.xlsx`);
}

}
