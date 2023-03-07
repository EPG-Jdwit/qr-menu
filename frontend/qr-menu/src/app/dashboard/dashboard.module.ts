import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSidenavModule} from '@angular/material/sidenav';

import { DashboardViewComponent } from './dashboard-view/dashboard-view.component';
import { LoginWindowComponent } from './login/login-window/login-window.component';
import { DashboardNavComponent } from './dashboard-nav/dashboard-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ProductTableComponent } from './product-table/product-table.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DashboardOverviewComponent } from './dashboard-overview/dashboard-overview.component';
import { ProductInfoComponent } from './product/product-info/product-info.component';
import { MatDialogModule } from '@angular/material/dialog';



@NgModule({
  declarations: [
    DashboardViewComponent,
    LoginWindowComponent,
    DashboardNavComponent,
    ProductTableComponent,
    DashboardOverviewComponent,
    ProductInfoComponent
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
    MatDialogModule
  ]
})
export class DashboardModule { }
