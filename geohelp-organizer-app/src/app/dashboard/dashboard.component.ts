import { Component, OnInit } from '@angular/core';
import {BackendService} from "../backend.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../api/login-request";
import {LoginResponse} from "../api/login-response";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  // passphrase and event name used to make a login request, bounded in the html file to the input fields
  public eventName: string = '';
  public passphrase: string = '';

  constructor(private backendService: BackendService, private router: Router) { }

  ngOnInit(): void {
  }

  public onLogin(): void {
    // make a login request to the backend
    const req: LoginRequest = new LoginRequest(this.eventName, this.passphrase);
    this.backendService.login(req).subscribe((response => {
      const data: LoginResponse = response.data;
      // only proceed if the login was successful
      if (data.success) {
        // store the passphrase and eventID in the session storage as it will be used as authentication for future monitoring requests
        sessionStorage.setItem('eventID', String(data.eventID));
        sessionStorage.setItem('passphrase', this.passphrase);
        // proceed to the monitoring page
        this.router.navigate(['event-monitoring']);
      }
    }));
  }

}
