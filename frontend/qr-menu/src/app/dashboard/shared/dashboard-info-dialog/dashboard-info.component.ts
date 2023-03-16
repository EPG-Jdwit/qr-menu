import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-dashboard-info',
    templateUrl: './dashboard-info.component.html',
    styleUrls: ['./dashboard-info.component.scss']
})
export class DashboardInfoComponent {
    constructor(
        public dialogRef: MatDialogRef<DashboardInfoComponent>,
        // TODO: data: Entity
        @Inject(MAT_DIALOG_DATA) public data: any,
      ) {}
    
      onNoClick(): void {
        this.dialogRef.close();
      }
      edit(): void {
        this.dialogRef.close(this.data.id);
      }
}
