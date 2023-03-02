import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManagementViewComponent } from './management/management-view/management-view.component';
import { PublicViewComponent } from './public/public-view/public-view.component';

import { CategoryListComponent } from './public/modules/category/components/category-list/categoryList.component';
import { CategoryContentComponent } from './public/modules/category/components/category-content/category-content.component';
import { HomeViewComponent } from './public/modules/home/components/home-view/home-view.component';
import { AboutViewComponent } from './public/modules/about/components/about-view/about-view.component';
import { ContactViewComponent } from './public/modules/contact/components/contact-view/contact-view.component';
import { BookingViewComponent } from './public/modules/booking/components/booking-view/booking-view.component';

const routes: Routes = [
  { path: 'management', component: ManagementViewComponent },
  { 
    path: '', 
    component: PublicViewComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full'},
      { path: 'home', component: HomeViewComponent},
      { path: 'categories', component: CategoryListComponent },
      { path: 'categories/:categoryId', component: CategoryContentComponent },
      { path: 'contact', component: ContactViewComponent},
      { path: 'booking', component: BookingViewComponent},
      { path: 'about', component: AboutViewComponent },
      { path: '**', component: HomeViewComponent },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
