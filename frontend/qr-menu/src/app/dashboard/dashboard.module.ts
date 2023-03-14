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
import { BaseTableComponent } from './shared/dashboard-table/base-table.component';
import { DashboardInfoComponent } from './shared/dashboard-info/dashboard-info.component';
import { DashboardNewComponent } from './shared/dashboard-new/dashboard-new.component';
import { DashboardEditComponent } from './shared/dashboard-edit/dashboard-edit.component';


import { ProductTableComponent } from './product/product-table/product-table.component';
import { ProductInfoComponent } from './product/product-info/product-info.component';
import { ProductEditViewComponent } from './product/product-edit-view/product-edit-view.component';
import { NewProductViewComponent } from './product/new-product-view/new-product-view.component';
import { NewProductSubViewComponent } from './product/new-product-view/new-product-sub-view/new-product-sub-view.component';
import { SubEditProductViewComponent } from './product/product-edit-view/sub-edit-product-view/sub-edit-product-view.component';


import { CategoryTableComponent } from './category/category-table/category-table.component';
import { CategoryInfoComponent } from './category/category-info/category-info.component';
import { NewCategoryViewComponent } from './category/new-category-view/new-category-view.component';
import { EditCategoryViewComponent } from './category/edit-category-view/edit-category-view.component';

import { SubcategoryTableComponent } from './subcategory/subcategory-table/subcategory-table.component';
import { SubcategoryInfoComponent } from './subcategory/subcategory-info/subcategory-info.component';
import { NewSubcategoryViewComponent } from './subcategory/new-subcategory-view/new-subcategory-view.component';
import { EditSubcategoryViewComponent } from './subcategory/edit-subcategory-view/edit-subcategory-view.component';
import { NewSubcategorySubViewComponent } from './subcategory/new-subcategory-view/new-subcategory-sub-view/new-subcategory-sub-view.component';

import { DeleteConfirmationComponent } from './shared/dashboard-table/delete-confirmation/delete-confirmation.component';
import { EditSubcategorySubViewComponent } from './subcategory/edit-subcategory-view/edit-subcategory-sub-view/edit-subcategory-sub-view.component';


@NgModule({
  declarations: [
    DashboardViewComponent,
    DashboardNavComponent,
    ProductTableComponent,
    DashboardOverviewComponent,
    ProductInfoComponent,
    ProductEditViewComponent,
    NewProductViewComponent,
    CategoryTableComponent,
    DashboardInfoComponent,
    CategoryInfoComponent,
    SubcategoryTableComponent,
    SubcategoryInfoComponent,
    BaseTableComponent,
    NewCategoryViewComponent,
    EditCategoryViewComponent,
    DashboardNewComponent,
    NewProductSubViewComponent,
    DashboardEditComponent,
    SubEditProductViewComponent,
    DeleteConfirmationComponent,
    NewSubcategoryViewComponent,
    EditSubcategoryViewComponent,
    NewSubcategorySubViewComponent,
    EditSubcategorySubViewComponent,
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
