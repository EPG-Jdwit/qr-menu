import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Product } from 'src/app/models/product.model';
import { DashboardEditComponent } from '../../shared/dashboard-edit-dialog/dashboard-edit.component';
import { ProductDashboardService } from '../product-dashboard.service';

@Component({
  selector: 'app-product-edit-view',
  templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
  styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class ProductEditViewComponent extends DashboardEditComponent {

  constructor(
    public override dialogRef: MatDialogRef<DashboardEditComponent>,
    @Inject(MAT_DIALOG_DATA) public override data: Product,
    protected override service : ProductDashboardService
  ) {
    super(dialogRef, data, service)

    this.entityForm.addControl('price', new FormControl('', [Validators.min(0)]));
    this.entityForm.addControl('description', new FormControl('', ));

    // Set selected allergenic values in the multi select
    if (this.data.allergenicList) {

      this.allergenicFormControl.setValue(
        // Capitalize the items
        this.data.allergenicList.map(item => item.charAt(0).toUpperCase() + item.slice(1))
        );
    }
  }

  protected override copyChanges(): void {
    if (this.entityForm.value.name) {
      this.data.name = this.entityForm.value.name;
    }
    if (this.entityForm.value.price) {
      this.data.price = +this.entityForm.value.price;
    }
    if (this.entityForm.value.description) {
      this.data.description = this.entityForm.value.description;
    }
    if (this.allergenicFormControl.value) {
      this.data.allergenicList = this.allergenicFormControl.value.map(item => item.toLowerCase());
    }
  }

  override isType(type: string): boolean {
    return type == "Product";
  }
}
