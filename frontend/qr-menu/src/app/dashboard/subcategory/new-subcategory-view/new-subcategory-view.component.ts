import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Subcategory } from 'src/app/models/subcategory.model';
import { SubcategoryDashboardService } from '../subcategory-dashboard.service';
import { DashboardNewComponent } from '../../shared/dashboard-new-dialog/dashboard-new.component';

@Component({
    selector: 'app-new-subcategory-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class NewSubcategoryViewComponent extends DashboardNewComponent {
    constructor(
        public override dialogRef: MatDialogRef<DashboardNewComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Subcategory,
        protected override service : SubcategoryDashboardService
    ) {
        super(dialogRef, data, service);

        // this.entityForm.removeControl('categoryFormControl');
        // this.entityForm.addControl('categoryFormControl', new FormControl('', [Validators.required]));
    }

    // Copy values from the form into the data Subcategory object
    protected override copyChanges(): void {
        this.data = {name: this.entityForm.value.name, category:this.categoryFormControl.value};
    }

    override isType(type: string): boolean {
        return type === "Subcategory";
    }
}
