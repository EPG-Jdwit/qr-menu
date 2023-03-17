import { Component, Inject } from '@angular/core';
import { Validators, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Entity } from 'src/app/models/entity.model';
import { AbstractDashboardService } from '../abstract-dashboard.service';

@Component({
    selector: 'app-dashboard-new',
    templateUrl: '../dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../dashboard-dialog/dashboard-dialog.component.scss']
})
export class DashboardDialogComponent {
    title: string = "";
    entityForm: FormGroup = new FormGroup({
        name: new FormControl(this.data? this.data.name : '', [
        ]),
        // allergenic: new FormControl('', [

        // ]),
        // category: new FormControl('', [

        // ]),
        // products: new FormControl('', [

        // ])
      })
    // TODO: This has to somehow be moved to NewProductViewComponent: Composition?
    allergenicFormControl = new FormControl();

    // TODO: This has to somehow be moved to NewSubcategoryViewComponent: Composition?
    categoryFormControl = new FormControl();

    // TODO: same
    productFormControl = new FormControl();
    
    constructor(
        public dialogRef: MatDialogRef<DashboardDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: Entity,
        protected service : AbstractDashboardService
    ) {
    }

    // Simply close the dialog
    close(): void {
        this.dialogRef.close();
    }

    // Save the new entity
    saveEntity(): void {
        // should be abstract
    }

    // Copy values from the form into the data Entity object
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
