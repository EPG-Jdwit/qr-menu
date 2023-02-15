import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';

import { SubcategoryModule } from '../subcategory/subcategory.module';
import { CategoryListComponent } from './components/category-list/categoryList.component';
import { CategoryContentComponent } from './components/category-content/category-content.component';


@NgModule({
  declarations: [
    CategoryListComponent,
    CategoryContentComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SubcategoryModule,
  ]
})
export class CategoryModule { }
