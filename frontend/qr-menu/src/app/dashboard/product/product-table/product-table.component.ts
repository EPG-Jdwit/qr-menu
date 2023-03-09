import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ProductDashboardService } from '../product-dashboard.service';
import { BaseTableComponent } from '../../common/dashboard-table/base-table.component';

@Component({
  selector: 'product-table',
  templateUrl: './product-table.component.html',
  styleUrls: ['./product-table.component.scss']
})
export class ProductTableComponent extends BaseTableComponent {

  constructor(
      public override service : ProductDashboardService,
      public override dialog: MatDialog) {
        super(service, dialog);
        this.embeddedList = 'productList';
        this.insertDisplayedColumn("price");
  }
}