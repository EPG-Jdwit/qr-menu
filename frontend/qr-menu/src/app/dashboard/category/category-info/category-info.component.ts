import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Category } from 'src/app/models/category.model';

import { DashboardInfoComponent } from '../../shared/dashboard-info-dialog/dashboard-info.component';

@Component({
	selector: 'app-category-info',
	templateUrl: './category-info.component.html',
	styleUrls: ['../../shared/dashboard-info-dialog/dashboard-info.component.scss']
})
export class CategoryInfoComponent extends DashboardInfoComponent {
    constructor(
		public override dialogRef: MatDialogRef<CategoryInfoComponent>,
		// TODO: data: Category
		@Inject(MAT_DIALOG_DATA) public override data: Category,
	  ) {
		super(dialogRef, data);
	  }
}
