import {Component, Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Product } from 'src/app/models/product.model';

import { DashboardInfoComponent } from '../../shared/dashboard-info-dialog/dashboard-info.component';

@Component({
    selector: 'app-product-info',
	templateUrl: './product-info.component.html',
    styleUrls: ['../../shared/dashboard-info-dialog/dashboard-info.component.scss']
})
export class ProductInfoComponent extends DashboardInfoComponent {
    constructor(
        public override dialogRef: MatDialogRef<ProductInfoComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Product,
    ) {
        super(dialogRef, data);
    }
}
