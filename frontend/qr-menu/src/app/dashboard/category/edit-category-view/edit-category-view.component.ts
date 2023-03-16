import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Category } from 'src/app/models/category.model';
import { DashboardEditComponent } from '../../shared/dashboard-edit-dialog/dashboard-edit.component';
import { CategoryDashboardService } from '../category-dashboard.service';


@Component({
    selector: 'app-edit-category-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class EditCategoryViewComponent extends DashboardEditComponent {
    constructor(
        public override dialogRef: MatDialogRef<DashboardEditComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Category,
        protected override service : CategoryDashboardService,
      ) {
        super(dialogRef, data, service)
      }
}
