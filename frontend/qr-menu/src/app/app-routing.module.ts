import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryListComponent } from './modules/category/components/category-list/categoryList.component';
import { CategoryContentComponent } from './modules/category/components/category-content/category-content.component';
import { HomeViewComponent } from './modules/home/components/home-view/home-view.component';
import { AboutViewComponent } from './modules/about/components/about-view/about-view.component';
import { ContactViewComponent } from './modules/contact/components/contact-view/contact-view.component';
import { BookingViewComponent } from './modules/booking/components/booking-view/booking-view.component';

const routes: Routes = [
  { path: '', component: HomeViewComponent},
  { path: 'categories', component: CategoryListComponent },
  { path: 'categories/:categoryId', component: CategoryContentComponent },
  { path: 'contact', component: ContactViewComponent},
  { path: 'booking', component: BookingViewComponent},
  { path: 'about', component: AboutViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
