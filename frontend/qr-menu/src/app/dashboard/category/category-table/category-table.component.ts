import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { BaseTableComponent } from '../../shared/dashboard-table/base-table.component';
import { CategoryDashboardService } from '../category-dashboard.service';

@Component({
    selector: 'category-table',
    templateUrl: '../../shared/dashboard-table/base-table.component.html',
    styleUrls: ['../../shared/dashboard-table/base-table.component.scss']
})
export class CategoryTableComponent extends BaseTableComponent {

    constructor(
        public override service: CategoryDashboardService,
        public override dialog: MatDialog
    ) {
        super(service, dialog);
        this.embeddedList = 'categoryList';
        this.type = 'Category';
        this.plural = 'Categories';
    }
    
}
