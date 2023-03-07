import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { LoginService } from '../login.service';

@Component({
  selector: 'app-login-window',
  templateUrl: './login-window.component.html',
  styleUrls: ['./login-window.component.scss']
})
export class LoginWindowComponent {
  constructor(private app: LoginService, private http: HttpClient, private router: Router) {
    this.app.authenticate(undefined, undefined);
  }
  logout() {
    this.http.post('logout', {}).subscribe(() => {
        this.app.authenticated = false;
        // this.router.navigateByUrl('/login');
        console.log("logged out :" + this.app.authenticated);
    });
  }
}
