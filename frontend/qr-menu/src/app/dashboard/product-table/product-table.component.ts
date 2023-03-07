import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';

import { ProductTableDataSource } from './product-table-datasource';
import { Product } from 'src/app/public/modules/product/product.model';
import { ProductTableService } from './product-table.service';

@Component({
  selector: 'product-table',
  templateUrl: './product-table.component.html',
  styleUrls: ['./product-table.component.scss']
})
export class ProductTableComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<Product>;
  dataSource: ProductTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'price', 'actions'];

  constructor(private productTableService : ProductTableService) {
    this.dataSource = new ProductTableDataSource();
  }

  ngOnInit() {

    this.productTableService.getProducts()
      .subscribe(
      products => {
        this.dataSource.data = products._embedded.productList;
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
      });
  }

  ngAfterViewInit(): void {
    // this.dataSource.sort = this.sort;
    // this.dataSource.paginator = this.paginator;
    // this.table.dataSource = this.dataSource;
  }
}
