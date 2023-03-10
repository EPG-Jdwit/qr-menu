import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { AbstractDashboardService } from '../abstract-dashboard.service';

@Component({
    selector: 'app-dashboard-new',
    templateUrl: '../../shared/dashboard-new/dashboard-new.component.html',
    styleUrls: ['../../shared/dashboard-new/dashboard-new.component.scss']
})
export class DashboardNewComponent {
    entityForm: FormGroup = new FormGroup({
        name: new FormControl('', [
          Validators.required
        ]),
      })
    // TODO: This has to somehow be moved to NewProductViewComponent: Composition?
    allergenicFormControl = new FormControl();

    
    constructor(
        public dialogRef: MatDialogRef<DashboardNewComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        protected service : AbstractDashboardService
    ) {}

    // Simply close the dialog
    close(): void {
        this.dialogRef.close();
    }

    // Save the new product
    saveChanges(): void {
        this.copyChanges();

        // Save the product to the backend
        this.service.createEntity(this.data).subscribe(response =>
            // Close the dialog and return the product (with assigned ID) front the backend
            this.dialogRef.close(response)
        );
    }

    // Copy values from the form into the data Product object
    protected copyChanges(): void {
        // should be abstract
    }

    // Methods to retrieve members of the FormGroup
    get name() {
        return this.entityForm.get("name");
    }

    // Error messages
    getNameErrorMessage(): string{
        return 'Name is required';
    }

    isType(type: string): boolean {
        return false;
    }
}
