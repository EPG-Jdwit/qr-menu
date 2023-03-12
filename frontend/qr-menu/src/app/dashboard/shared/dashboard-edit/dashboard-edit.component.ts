import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Entity } from 'src/app/models/entity.model';
import { AbstractDashboardService } from '../abstract-dashboard.service';

@Component({
    selector: 'app-dashboard-edit',
    templateUrl: './dashboard-edit.component.html',
    styleUrls: ['./dashboard-edit.component.scss']
})
export class DashboardEditComponent {
    entityForm: FormGroup = new FormGroup({
      name: new FormControl('', [
        
      ]),
    })
    // TODO: This has to somehow be moved to EditProductViewComponent: Composition?
    allergenicFormControl = new FormControl();
  
    constructor(
      public dialogRef: MatDialogRef<DashboardEditComponent>,
      @Inject(MAT_DIALOG_DATA) public data: Entity,
      protected service : AbstractDashboardService
    ) {
    }
  
    // Simply close the dialog
    close(): void {
      this.dialogRef.close();
    }
  
    // Save the changes to the backend
    saveChanges(): void {
      this.copyChanges();
      this.service.editEntity(this.data);
    }
  
    protected copyChanges(): void {
      if (this.entityForm.value.name) {
        this.data.name = this.entityForm.value.name;
      }
    }

    isType(type: string): boolean {
        return false;
    }
}
