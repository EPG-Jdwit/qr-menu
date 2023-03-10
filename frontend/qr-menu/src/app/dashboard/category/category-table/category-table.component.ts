import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Category } from 'src/app/models/category.model';
import { BaseTableComponent } from '../../shared/dashboard-table/base-table.component';
import { CategoryDashboardService } from '../category-dashboard.service';
import { EditCategoryViewComponent } from '../edit-category-view/edit-category-view.component';
import { NewCategoryViewComponent } from '../new-category-view/new-category-view.component';

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
        this.insertDisplayedColumn("nSubcategories");
        this.type = 'Category';
        this.plural = 'Categories';
    }

    // Edit the entity
    override editEntity(category: Category): void {

        this.dialog.open(EditCategoryViewComponent, {
            data: category
        });
    }

    // Add a new entity
    override createEntity(): void {
        let newEntity: Category;
        const dialogRef = this.dialog.open(NewCategoryViewComponent, {
            data: newEntity
        });
        dialogRef.afterClosed().subscribe(result => {
            // Ignore when the dialog was closed by canceling
            if (result) {
                // Add the created product to the table
                this.dataSource.data.push(result);
                this.dataSource.data = this.dataSource.data;
            }
        });
    }
    
}
