import {Component, Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import { DashboardInfoComponent } from '../../shared/dashboard-info/dashboard-info.component';

@Component({
    selector: 'app-product-info',
    templateUrl: '../../shared/dashboard-info/dashboard-info.component.html',
    styleUrls: ['../../shared/dashboard-info/dashboard-info.component.scss']
})
export class ProductInfoComponent extends DashboardInfoComponent {
    constructor(
        public override dialogRef: MatDialogRef<DashboardInfoComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: any,
    ) {
        super(dialogRef, data);
    }
}
