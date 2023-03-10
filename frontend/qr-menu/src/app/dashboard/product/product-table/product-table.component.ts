import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ProductDashboardService } from '../product-dashboard.service';
import { BaseTableComponent } from '../../shared/dashboard-table/base-table.component';
import { ProductEditViewComponent } from '../product-edit-view/product-edit-view.component';
import { NewProductViewComponent } from '../new-product-view/new-product-view.component';
import { Product } from 'src/app/models/product.model';

@Component({
    selector: 'product-table',
    templateUrl: '../../shared/dashboard-table/base-table.component.html',
    styleUrls: ['../../shared/dashboard-table/base-table.component.scss']
})
export class ProductTableComponent extends BaseTableComponent {

  constructor(
      public override service : ProductDashboardService,
      public override dialog: MatDialog) {
        super(service, dialog);
        this.embeddedList = 'productList';
        this.insertDisplayedColumn("price");
        this.type = 'Product';
        this.plural = 'Products';
  }
  
    // Edit the entity
    override editEntity(product: Product): void {
        this.dialog.open(ProductEditViewComponent, {
            data: product
        });
    }

    // Add a new entity
    override createEntity(): void {
        let newEntity: Product;
        const dialogRef = this.dialog.open(NewProductViewComponent, {
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