import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ButtonsModule } from '../../common/buttons/buttons.module';
import { SubcategoryListComponent } from './components/subcategory-list/subcategory-list.component';
import { SubcategoryProductListComponent } from './components/subcategory-product-list/subcategory-product-list.component';
import { ProductModule } from '../product/product.module';

@NgModule({
  declarations: [SubcategoryListComponent, SubcategoryProductListComponent],
  imports: [
    CommonModule,
    ButtonsModule,
    ProductModule
  ],
  exports: [
    SubcategoryListComponent
  ]
})
export class SubcategoryModule { }
