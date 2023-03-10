import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { BaseTableComponent } from '../../shared/dashboard-table/base-table.component';
import { SubcategoryDashboardService } from '../subcategory-dashboard.service';

@Component({
    selector: 'app-subcategory-table',
    templateUrl: '../../shared/dashboard-table/base-table.component.html',
    styleUrls: ['../../shared/dashboard-table/base-table.component.scss']
})
export class SubcategoryTableComponent extends BaseTableComponent {
    
    constructor(
        public override service: SubcategoryDashboardService,
        public override dialog: MatDialog
    ) {
        super(service, dialog);
        this.embeddedList = 'subcategoryList';
        this.insertDisplayedColumn("category");
        this.type = 'Subcategory';
        this.plural = 'Subcategories';
    }

    // // Delete the entity
    // override deleteById(id: number): void {
    //     // Delete from the backend
    //     this.service.deleteById(id);
    //     // Delete from the frontend TODO: error handling NotFound
    //     const index = this.dataSource.data.findIndex(entity => entity.id == id);
    //     this.dataSource.data.splice(index, 1);
    //     this.dataSource.data = this.dataSource.data;
    // }

    // Edit the entity
    override editById(id: number): void {
        // const index = this.dataSource.data.findIndex(product => product.id == id);
        // const item = this.dataSource.data[index];
        // this.dialog.open(EditCategoryViewComponent, {
        //     data: item
        // });
    }

    // Add a new entity
    override create(): void {
        // let newEntity: Category;
        // const dialogRef = this.dialog.open(NewCategoryViewComponent, {
        //     data: newEntity
        // });
        // dialogRef.afterClosed().subscribe(result => {
        //     // Ignore when the dialog was closed by canceling
        //     if (result) {
        //         // Add the created product to the table
        //         this.dataSource.data.push(result);
        //         this.dataSource.data = this.dataSource.data;
        //     }
        // });
    }
}
