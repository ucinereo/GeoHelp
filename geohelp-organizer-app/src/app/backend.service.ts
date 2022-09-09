import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {EventRegisterRequest} from "./api/event-register-request";
import {LoginRequest} from "./api/login-request";
import {ApproveEmergencyRequest} from "./api/approve-emergency-request";
import {PullEmergenciesOrganizerRequest} from "./api/pull-emergencies-organizer-request";

@Injectable({
  providedIn: 'root'
})

// this class does all the communication with the backend
export class BackendService {

  // the IP-Address of the backend
  public static IP = "http://10.5.95.155:8080/";

  constructor(private httpClient: HttpClient) { }

  // request to register a new event
  public registerEvent(event: EventRegisterRequest) {
    return this.httpClient.post<any>(BackendService.IP + "register-event", event);
  }

  // request to login to monitor a event, check passphrase with the corresponding event name
  public login(request: LoginRequest) {
    return this.httpClient.post<any>(BackendService.IP + "login", request);
  }

  // poll all new emergencies (emergencyID greater than the one send to the backend) from the backend
  public pullEmergencies(request: PullEmergenciesOrganizerRequest) {
    return this.httpClient.post<any>(BackendService.IP + "pull-emergencies", request);
  }

  // request the backend to approve an emergency, which will trigger the alarm on all nearby devices
  public approveEmergency(request: ApproveEmergencyRequest) {
    return this.httpClient.post<any>(BackendService.IP + "approve-emergency", request);
  }

}
