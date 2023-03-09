import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSidenavModule} from '@angular/material/sidenav';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LayoutModule } from '@angular/cdk/layout';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';

import { DashboardViewComponent } from './dashboard-view/dashboard-view.component';
import { DashboardOverviewComponent } from './dashboard-overview/dashboard-overview.component';
import { DashboardNavComponent } from './dashboard-nav/dashboard-nav.component';
import { DashboardInfoComponent } from './shared/dashboard-info/dashboard-info.component';

import { ProductTableComponent } from './product/product-table/product-table.component';
import { ProductInfoComponent } from './product/product-info/product-info.component';
import { ProductEditViewComponent } from './product/product-edit-view/product-edit-view.component';
import { NewProductViewComponent } from './product/new-product-view/new-product-view.component';

import { CategoryTableComponent } from './category/category-table/category-table.component';
import { ProductFormComponent } from './product/product-form/product-form.component';
import { CategoryInfoComponent } from './category/category-info/category-info.component';

@NgModule({
  declarations: [
    DashboardViewComponent,
    DashboardNavComponent,
    ProductTableComponent,
    DashboardOverviewComponent,
    ProductInfoComponent,
    ProductEditViewComponent,
    NewProductViewComponent,
    ProductFormComponent,
    CategoryTableComponent,
    DashboardInfoComponent,
    CategoryInfoComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatSidenavModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule
  ]
})
export class DashboardModule { }
