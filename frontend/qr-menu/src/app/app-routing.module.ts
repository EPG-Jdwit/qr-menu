import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryListComponent } from './modules/category/components/category-list/categoryList.component';
import { CategoryContentComponent } from './modules/category/components/category-content/category-content.component';

const routes: Routes = [
  { path: 'categories', component: CategoryListComponent },
  { path: 'categories/:categoryId', component: CategoryContentComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
