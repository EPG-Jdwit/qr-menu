import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.component.html',
  styleUrls: ['./contact-view.component.scss']
})
export class ContactViewComponent {
  addresText: string;
  emailText: string;
  hoursText: string;
  phoneText: string;
  private addresTextUrl: string = "assets/contact-addres.txt";
  private emailTextUrl: string = "assets/contact-email.txt";
  private hoursTextUrl: string = "assets/contact-hours.txt";
  private phoneTextUrl: string = "assets/contact-phone.txt";

  center: google.maps.LatLngLiteral;
  markers: any[] = [];

  constructor(private http: HttpClient) {
    this.center = {
      lat: 51.13321479515517, 
      lng: 2.673087821281197
    }

    this.markers.push({
      position: {
        lat: this.center.lat,
        lng: this.center.lng
      },
      label: {
        color: 'red',
        text: 'De Barkentijn Oostduinkerke',
      },
      title: 'De Barkentijn Oostduinkerke',
      options: { animation: google.maps.Animation.BOUNCE },
    });
  }

  ngOnInit(): void {
    this.http.get(this.addresTextUrl, {responseType: 'text'}).subscribe(data => {
      this.addresText = data;
    })
    this.http.get(this.emailTextUrl, {responseType: 'text'}).subscribe(data => {
      this.emailText = data;
    })
    this.http.get(this.hoursTextUrl, {responseType: 'text'}).subscribe(data => {
      this.hoursText = data;
    })
    this.http.get(this.phoneTextUrl, {responseType: 'text'}).subscribe(data => {
      this.phoneText = data;
    })
  }

  
}
