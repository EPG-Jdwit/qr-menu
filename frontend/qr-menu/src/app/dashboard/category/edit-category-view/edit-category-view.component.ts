import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Category } from 'src/app/models/category.model';
import { DashboardEditComponent } from '../../shared/dashboard-edit/dashboard-edit.component';
import { CategoryDashboardService } from '../category-dashboard.service';


@Component({
    selector: 'app-edit-category-view',
    templateUrl: '../../shared/dashboard-edit/dashboard-edit.component.html',
    styleUrls: ['../../shared/dashboard-edit/dashboard-edit.component.scss']
})
export class EditCategoryViewComponent extends DashboardEditComponent {
    constructor(
        public override dialogRef: MatDialogRef<DashboardEditComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Category,
        protected override service : CategoryDashboardService
      ) {
        super(dialogRef, data, service)
      }
}
