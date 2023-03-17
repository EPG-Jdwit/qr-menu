import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Entity } from 'src/app/models/entity.model';
import { AbstractDashboardService } from '../abstract-dashboard.service';
import { DashboardDialogComponent } from '../dashboard-dialog/dashboard-dialog.component';

@Component({
    selector: 'app-dashboard-new',
    templateUrl: '../dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../dashboard-dialog/dashboard-dialog.component.scss']
})
export class DashboardNewComponent extends DashboardDialogComponent {
    override title: string = "New";
    
    constructor(
        public override dialogRef: MatDialogRef<DashboardDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Entity,
        protected override service : AbstractDashboardService
    ) {
        super(dialogRef, data, service);
        this.entityForm.setControl('name', new FormControl('', Validators.required));
    }

    // Save the new entity
    override saveEntity(): void {
        this.copyChanges();

        // Save the entity to the backend
        this.service.createEntity(this.data).subscribe(response =>
            // Close the dialog and return the entity (with assigned ID) from the backend
            this.dialogRef.close(response)
        );
    }

    // Copy values from the form into the data Entity object
    protected override copyChanges(): void {
        // should be abstract
    }
}
