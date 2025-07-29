import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

    selectedTab = 0;

  used = 5;
  pending = 2;
  total = 12;

  recentLeaves = [
    { date: '2025-07-25', type: 'ลาป่วย', days: 1, status: 'อนุมัติ' },
    { date: '2025-07-20', type: 'ลากิจ', days: 2, status: 'รออนุมัติ' },
  ];

}
