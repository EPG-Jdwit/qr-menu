import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Category } from 'src/app/models/category.model';
import { DashboardNewComponent } from '../../shared/dashboard-new-dialog/dashboard-new.component';
import { CategoryDashboardService } from '../category-dashboard.service';

@Component({
    selector: 'app-new-category-view',
    templateUrl: '../../shared/dashboard-dialog/dashboard-dialog.component.html',
    styleUrls: ['../../shared/dashboard-dialog/dashboard-dialog.component.scss']
})
export class NewCategoryViewComponent extends DashboardNewComponent {
    
    constructor(
        public override dialogRef: MatDialogRef<NewCategoryViewComponent>,
        @Inject(MAT_DIALOG_DATA) public override data: Category,
        protected override service : CategoryDashboardService
    ) {
        super(dialogRef, data, service);
    }
    
    
    // Copy values from the form into the data Product object
    protected override copyChanges(): void {
        this.data = {name: this.entityForm.value.name};
    }

    override isType(type: string): boolean {
        return type === "Category";
    }
}
