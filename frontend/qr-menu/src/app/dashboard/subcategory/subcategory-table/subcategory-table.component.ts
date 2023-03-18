import { NoopScrollStrategy } from '@angular/cdk/overlay';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subcategory } from 'src/app/models/subcategory.model';

import { BaseTableComponent } from '../../shared/dashboard-table/base-table.component';
import { EditSubcategoryViewComponent } from '../edit-subcategory-view/edit-subcategory-view.component';
import { NewSubcategoryViewComponent } from '../new-subcategory-view/new-subcategory-view.component';
import { SubcategoryDashboardService } from '../subcategory-dashboard.service';
import { SubcategoryInfoComponent } from '../subcategory-info/subcategory-info.component';

@Component({
    selector: 'app-subcategory-table',
    templateUrl: '../../shared/dashboard-table/base-table.component.html',
    styleUrls: ['../../shared/dashboard-table/base-table.component.scss']
})
export class SubcategoryTableComponent extends BaseTableComponent {
    
    constructor(
        public override service: SubcategoryDashboardService,
        public override dialog: MatDialog ) {

        super(service, dialog);
        this.embeddedList = 'subcategoryList';
        this.insertDisplayedColumn("category");
        this.type = 'Subcategory';
        this.plural = 'Subcategories';
    }

    // Add a new entity
    override createEntity(): void {
        let newEntity: Subcategory;
        const dialogRef = this.dialog.open(NewSubcategoryViewComponent, {
            data: newEntity,
            scrollStrategy: new NoopScrollStrategy()
        });
        dialogRef.afterClosed().subscribe(result => {
            // Ignore when the dialog was closed by canceling
            if (result) {
                console.log(result);
                // Add the created entity to the table
                this.dataSource.data.push(result);
                this.dataSource.data = this.dataSource.data;
            }
        });
    }

    override editEntity(subcategory: Subcategory): void {
        this.dialog.open(EditSubcategoryViewComponent, {
            data: subcategory,
            scrollStrategy: new NoopScrollStrategy()
        });
    }

    // Open a specific infoComponent depending on the parameter
    override showEntityInfo(subcategory: Subcategory): void {
        this.showInfo(subcategory, SubcategoryInfoComponent);
    }
}
