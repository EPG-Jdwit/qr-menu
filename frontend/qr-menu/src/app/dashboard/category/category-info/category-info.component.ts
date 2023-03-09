import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import { DashboardInfoComponent } from '../../shared/dashboard-info/dashboard-info.component';

@Component({
	selector: 'app-category-info',
	templateUrl: '../../shared/dashboard-info/dashboard-info.component.html',
	styleUrls: ['../../shared/dashboard-info/dashboard-info.component.scss']
})
export class CategoryInfoComponent extends DashboardInfoComponent {
    constructor(
		public override dialogRef: MatDialogRef<DashboardInfoComponent>,
		// TODO: data: Category
		@Inject(MAT_DIALOG_DATA) public override data: any,
	  ) {
		super(dialogRef, data);
	  }
}
