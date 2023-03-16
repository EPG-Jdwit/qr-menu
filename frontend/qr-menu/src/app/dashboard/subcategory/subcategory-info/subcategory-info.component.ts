import {Component, Inject} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Subcategory } from 'src/app/models/subcategory.model';

import { DashboardInfoComponent } from '../../shared/dashboard-info-dialog/dashboard-info.component';

@Component({
  selector: 'app-subcategory-info',
  templateUrl: './subcategory-info.component.html',
	styleUrls: ['../../shared/dashboard-info-dialog/dashboard-info.component.scss']
})
export class SubcategoryInfoComponent extends DashboardInfoComponent {
    constructor(
		public override dialogRef: MatDialogRef<SubcategoryInfoComponent>,
		// TODO: data: Category
		@Inject(MAT_DIALOG_DATA) public override data: Subcategory,
	  ) {
		super(dialogRef, data);
	  }
}
