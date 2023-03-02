import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GoogleMapsModule } from '@angular/google-maps'
import { PublicViewComponent } from './public-view/public-view.component';
import { AppRoutingModule } from '../app-routing.module';

import { CategoryModule } from './modules/category/category.module';
import { HomeModule } from './modules/home/home.module';
import { ContactModule } from './modules/contact/contact.module';

@NgModule({
  declarations: [
    PublicViewComponent
  ],
  imports: [
    CommonModule,
    GoogleMapsModule,
    AppRoutingModule,
    CategoryModule,
    HomeModule,
    ContactModule
  ]
})
export class PublicModule { }
