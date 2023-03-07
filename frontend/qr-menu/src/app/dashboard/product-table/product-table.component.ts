import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';

import { Product } from 'src/app/public/modules/product/product.model';
import { ProductTableService } from './product-table.service';

@Component({
  selector: 'product-table',
  templateUrl: './product-table.component.html',
  styleUrls: ['./product-table.component.scss']
})
export class ProductTableComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<Product>;
  dataSource: MatTableDataSource<Product>;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'price', 'actions'];

  constructor(private productTableService : ProductTableService) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit() {

    this.productTableService.getProducts()
      .subscribe(
      products => {
        this.dataSource.data = products._embedded.productList;
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = function (product, filter) {
          return product.name.toLowerCase().includes(filter.trim().toLowerCase())

        }
        this.table.dataSource = this.dataSource;
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  deleteProduct(id: number) :void {
    this.productTableService.deleteProduct(id);
    const index = this.dataSource.data.findIndex(product => product.id == id);
    this.dataSource.data.splice(index, 1);
    this.dataSource.data = this.dataSource.data;
  }
}
