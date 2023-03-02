import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GoogleMapsModule } from '@angular/google-maps'
import { ContactViewComponent } from './components/contact-view/contact-view.component';

@NgModule({
  declarations: [
    ContactViewComponent
  ],
  imports: [
    CommonModule,
    GoogleMapsModule
  ]
})
export class ContactModule { }
