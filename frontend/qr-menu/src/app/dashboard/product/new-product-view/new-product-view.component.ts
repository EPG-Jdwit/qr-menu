import { Component, Inject } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Product } from 'src/app/models/product.model';
import { DashboardNewComponent } from '../../shared/dashboard-new/dashboard-new.component';
import { ProductDashboardService } from '../product-dashboard.service';

@Component({
    selector: 'app-new-product-view',
    templateUrl: '../../shared/dashboard-new/dashboard-new.component.html',
    styleUrls: ['../../shared/dashboard-new/dashboard-new.component.scss']
})
export class NewProductViewComponent extends DashboardNewComponent {
    constructor(
        public override dialogRef: MatDialogRef<DashboardNewComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Product,
        protected override service : ProductDashboardService
    ) {
        super(dialogRef, data, service);
        
        this.entityForm.addControl('price', new FormControl('', [Validators.required, Validators.min(0)]));
        this.entityForm.addControl('description', new FormControl('', ));
    }


    // Copy values from the form into the data Product object
    protected override copyChanges(): void {
        this.data = {name: this.entityForm.value.name, price: +this.entityForm.value.price, description: this.entityForm.value.description};

        // Add the selectet allergenics to the product
        if (this.allergenicFormControl.value) {
            this.data.allergenicList = this.allergenicFormControl.value.map((a : string) => a.toLowerCase());
        } else {
            // Create empty list
            this.data.allergenicList = [];
        }
    }

    override isType(type: string): boolean {
        return type === "Product";
    }
}
