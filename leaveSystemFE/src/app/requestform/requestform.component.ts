import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-requestform',
  templateUrl: './requestform.component.html',
  styleUrl: './requestform.component.css'
})
export class RequestformComponent {

  constructor(private http: HttpClient) { }


  leaveRequest = {
    userId: 1,
    leaveTypeId: null,
    reason: '',
    startDate: '',
    endDate: ''
  };

  resetForm(): void {
  this.leaveRequest = {
    userId: 1,           
    leaveTypeId: null,
    reason: '',
    startDate: '',
    endDate: ''
  };
}

  checkValidation(): boolean {
    if (!this.leaveRequest.leaveTypeId) {
      alert('กรุณาเลือกประเภทการลา');
      return false;
    }

    if (!this.leaveRequest.reason.trim()) {
      alert('กรุณากรอกเหตุผลในการลา');
      return false;
    }

    if (!this.leaveRequest.startDate) {
      alert('กรุณาเลือกวันเริ่มลา');
      return false;
    }

    if (!this.leaveRequest.endDate) {
      alert('กรุณาเลือกวันสิ้นสุดการลา');
      return false;
    }



    return true;
  }

  submitLeaveRequest(): void {
    if (!this.checkValidation()) {
      return;
    }
    this.http.post('http://localhost:8080/api/leave-requests', this.leaveRequest).subscribe({
      next: (res: any) => {
        console.log('ส่งคำขอลาสำเร็จ', res);
        this.resetForm();
        alert('ส่งคำขอลาสำเร็จ');
      },
      error: (err: any) => {
        console.error('ส่งคำขอลาไม่สำเร็จ', err);
        alert('ส่งคำขอลาไม่สำเร็จ');
      }
    });
  }
}
