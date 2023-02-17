import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.scss']
})
export class HomeViewComponent {
  homeText: string;
  private homeTextUrl: string = "assets/home.txt";

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.readTextFile();
  }

  private readTextFile(): void {
    this.http.get(this.homeTextUrl, {responseType: 'text'}).subscribe(data => {
      this.homeText = data;
    })
  }
}
