import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
// import {MatSnackBar} from '@angular/material/snack-bar';

import { Entity } from 'src/app/models/entity.model';
import { AbstractDashboardService } from '../abstract-dashboard.service';
import { DashboardDialogComponent } from '../dashboard-dialog/dashboard-dialog.component';

@Component({
    selector: 'app-dashboard-edit',
    templateUrl: '../dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../dashboard-dialog/dashboard-dialog.component.scss']
})
export class DashboardEditComponent extends DashboardDialogComponent {
    override title: string = "Edit";
  
    constructor(
      public override dialogRef: MatDialogRef<DashboardDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public override data: Entity,
      protected override service : AbstractDashboardService,
      // private _snackBar: MatSnackBar
    ) {
      super(dialogRef, data, service);
    }
  
  
    // Save the changes to the backend
    override saveEntity(): void {
      this.copyChanges();
      this.service.editEntity(this.data);
      // this._snackBar.open("Item edited", "Ok");
    }
  
    protected override copyChanges(): void {
      if (this.entityForm.value.name) {
        this.data.name = this.entityForm.value.name;
      }
    }
}
