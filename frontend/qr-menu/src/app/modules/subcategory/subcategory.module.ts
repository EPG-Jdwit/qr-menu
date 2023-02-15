import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ButtonsModule } from 'src/app/common/buttons/buttons.module';
import { SubcategoryListComponent } from './components/subcategory-list/subcategory-list.component';
import { SubcategoryProductListComponent } from './components/subcategory-product-list/subcategory-product-list.component';

@NgModule({
  declarations: [SubcategoryListComponent, SubcategoryProductListComponent],
  imports: [
    CommonModule,
    ButtonsModule
  ],
  exports: [
    SubcategoryListComponent
  ]
})
export class SubcategoryModule { }
