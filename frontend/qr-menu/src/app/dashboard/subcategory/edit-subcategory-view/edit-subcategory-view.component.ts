import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Subcategory } from 'src/app/models/subcategory.model';
import { Product } from 'src/app/models/product.model';
import { DashboardEditComponent } from '../../shared/dashboard-edit-dialog/dashboard-edit.component';
import { SubcategoryDashboardService } from '../subcategory-dashboard.service';

@Component({
    selector: 'app-edit-subcategory-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class EditSubcategoryViewComponent extends DashboardEditComponent {

    constructor(
        public override dialogRef: MatDialogRef<EditSubcategoryViewComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Subcategory,
        protected override service : SubcategoryDashboardService
      ) {
        super(dialogRef, data, service);

        // Set selected category value in the multi select
        if (this.data.category) {
            this.categoryFormControl.setValue(this.data.category);
        }

        if (this.data.productList) {
            // this.productFormControl.setValue(this.data.productList.map(product => product.name));
            this.productFormControl.setValue(this.data.productList);
        }
      }

    // Copy values from the form into the data Subcategory object
    protected override copyChanges(): void {
        if (this.entityForm.value.name) {
            this.data.name = this.entityForm.value.name;
        }
        if (this.categoryFormControl.value) {
            this.data.category = this.categoryFormControl.value
        }
        if (this.productFormControl.value) {
            this.data.productList = this.productFormControl.value
            this.data.products = this.productFormControl.value.map(product => product.id);
        }
    }

    override isType(type: string): boolean {
        return type === "Subcategory";
    }
}
