import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Subcategory } from 'src/app/models/subcategory.model';
import { DashboardEditComponent } from '../../shared/dashboard-edit-dialog/dashboard-edit.component';
import { SubcategoryDashboardService } from '../subcategory-dashboard.service';

@Component({
    selector: 'app-edit-subcategory-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class EditSubcategoryViewComponent extends DashboardEditComponent {

    constructor(
        public override dialogRef: MatDialogRef<DashboardEditComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Subcategory,
        protected override service : SubcategoryDashboardService
      ) {
        super(dialogRef, data, service)
      }

    // Copy values from the form into the data Subcategory object
    protected override copyChanges(): void {
        if (this.entityForm.value.name) {
            this.data.name = this.entityForm.value.name;
        }
        if (this.categoryFormControl.value) {
            this.data.category = this.categoryFormControl.value
        }
    }

    override isType(type: string): boolean {
        return type === "Subcategory";
    }
}
