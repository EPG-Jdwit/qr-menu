import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-about-view',
  templateUrl: './about-view.component.html',
  styleUrls: ['./about-view.component.scss']
})
export class AboutViewComponent {
  aboutText: string;
  private aboutTextUrl: string = "assets/about.txt";

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.readTextFile();
  }

  private readTextFile(): void {
    this.http.get(this.aboutTextUrl, {responseType: 'text'}).subscribe(data => {
      this.aboutText = data;
    })
  }
}
