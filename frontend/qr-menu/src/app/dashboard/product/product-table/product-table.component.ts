import { Component, ViewChild, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';

import { Product } from 'src/app/public/modules/product/product.model';
import { NewProductViewComponent } from '../new-product-view/new-product-view.component';
import { ProductEditViewComponent } from '../product-edit-view/product-edit-view.component';
import { ProductInfoComponent } from '../product-info/product-info.component';
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

  constructor(private productTableService : ProductTableService,
              public dialog: MatDialog
    ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit() {

    this.productTableService.getProducts()
      .subscribe(
      products => {
        // response only contains _embedded if products exist
        if (products._embedded) {
          this.dataSource.data = products._embedded.productList;
        }
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

  showProductInfo(id: number): void {
    const index = this.dataSource.data.findIndex(product => product.id == id);
    const item = this.dataSource.data[index];
    const dialogRef = this.dialog.open(ProductInfoComponent, {
      // height: '40vh',
      // width: '40vw',
      data: item
    });
  }
  editProduct(id: number): void {
    const index = this.dataSource.data.findIndex(product => product.id == id);
    const item = this.dataSource.data[index];
    const dialogRef = this.dialog.open(ProductEditViewComponent, {
      data: item
    });
  }

  newProduct(): void {
    let newProduct: Product;
    const dialogRef = this.dialog.open(NewProductViewComponent, {
      data: newProduct
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