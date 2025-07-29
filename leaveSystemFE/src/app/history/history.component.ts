import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Chart from 'chart.js/auto';

interface UserLeave {
  name: string;
  department: string;
  sickLeave: number;
  personalLeave: number;
  vacationLeave: number;
}

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
   selectedMonth = '2024-01';
  selectedDepartment = '';
  departments = ['Pending','Approved,Rejected'];
  
  users: UserLeave[] = [
    { name: 'สมชาย ใจดี', department: 'IT', sickLeave: 2, personalLeave: 1, vacationLeave: 0 },
    { name: 'สมหญิง รักงาน', department: 'HR', sickLeave: 1, personalLeave: 2, vacationLeave: 1 },
  ];

  filteredUsers: UserLeave[] = [];
  chart: any;

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    // กรองข้อมูลตามแผนก
    if (this.selectedDepartment) {
      this.filteredUsers = this.users.filter(u => u.department === this.selectedDepartment);
    } else {
      this.filteredUsers = [...this.users];
    }

    this.renderChart();
  }

  renderChart() {
    const sickSum = this.filteredUsers.reduce((sum, u) => sum + u.sickLeave, 0);
    const personalSum = this.filteredUsers.reduce((sum, u) => sum + u.personalLeave, 0);
    const vacationSum = this.filteredUsers.reduce((sum, u) => sum + u.vacationLeave, 0);

    if (this.chart) {
      this.chart.destroy();
    }

    const ctx = (document.getElementById('leaveChart') as HTMLCanvasElement).getContext('2d');
    this.chart = new Chart(ctx!, {
      type: 'bar',
      data: {
        labels: ['ลาป่วย', 'ลากิจ', 'ลาพักร้อน'],
        datasets: [{
          label: 'จำนวนวัน',
          data: [sickSum, personalSum, vacationSum],
          backgroundColor: 'rgba(54, 162, 235, 0.7)'
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: { beginAtZero: true }
        },
        plugins: {
          tooltip: {
            enabled: true
          }
        }
      }
    });
  }

  exportExcel() {
    // ใส่โค้ด export excel หรือแจ้งเตือนชั่วคราว
    alert('ฟังก์ชัน Export Excel ยังไม่ได้เชื่อมต่อ');
  }

}
