import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';

import { Product } from 'src/app/models/product.model';
import { NewProductViewComponent } from '../new-product-view/new-product-view.component';
import { ProductEditViewComponent } from '../product-edit-view/product-edit-view.component';
import { ProductInfoComponent } from '../product-info/product-info.component';
import { ProductDashboardService } from '../product-dashboard.service';

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

  constructor(private productService : ProductDashboardService,
              public dialog: MatDialog) {
    this.dataSource = new MatTableDataSource();
  }

  // Fetch all existing products for the table on initialization
  ngOnInit() {
    this.productService.getProducts()
      .subscribe(
      products => {
        // response only contains _embedded if products exist
        if (products._embedded) {
          this.dataSource.data = products._embedded.productList;
        }
        // Set paginator, sorting and filtering to the data source
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = function (product, filter) {
          return product.name.toLowerCase().includes(filter.trim().toLowerCase())
        }
        // Set the MatTableDataSource as source of the table's data
        this.table.dataSource = this.dataSource;
      });
  }

  // Filtering of the table by name (substring matching)
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  // Delete product
  deleteProduct(id: number) :void {
    // Delete from the backend
    this.productService.deleteProduct(id);
    // Delete from the frontend TODO: error handling NotFound
    const index = this.dataSource.data.findIndex(product => product.id == id);
    this.dataSource.data.splice(index, 1);
    this.dataSource.data = this.dataSource.data;
  }

  // Display a dialog with all the information about the product
  // with the posibility of editing it
  showProductInfo(id: number): void {
    const index = this.dataSource.data.findIndex(product => product.id == id);
    const item = this.dataSource.data[index];
    const dialogRef = this.dialog.open(ProductInfoComponent, {
      data: item
    });
    dialogRef.afterClosed().subscribe(id => {
      // Ignore when the dialog was closed by canceling
      if (id) {
        // Open the edit dialog for the product
        this.editProduct(id);
      }
    });
  }

  // Edit the product
  editProduct(id: number): void {
    const index = this.dataSource.data.findIndex(product => product.id == id);
    const item = this.dataSource.data[index];
    this.dialog.open(ProductEditViewComponent, {
      data: item
    });
  }

  // Add a new product
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

  // Scrolls to the top of the table when the next page in the paginator requested
  onPaginateChange(event: any) {
    const element = document.getElementById("scroll-top");
    element.scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
  }

  public handlePageBottom(event: PageEvent) {
    this.paginator.pageSize = event.pageSize;
    this.paginator.pageIndex = event.pageIndex;
    this.paginator.page.emit(event);
  }
}