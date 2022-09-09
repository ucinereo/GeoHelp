import { Component, OnInit } from '@angular/core';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit {

  // string to store all data needed for the qr code
  public qrCodeString: string = '';

  // passphrase for the event
  public passphrase: string = '';

  // name of the event
  public eventName: string = '';

  constructor() { }

  ngOnInit(): void {
    // create the qr code string, the qr code will be generated from this string in the html file, by a qr code component
    // all needed properties are stored in the session storage
    let qrParameter: Object = {
      'eventId': Number(sessionStorage.getItem('eventID')),
      'eventName': sessionStorage.getItem('eventName'),
      'startTime': Number(sessionStorage.getItem('startTime')),
      'endTime': Number(sessionStorage.getItem('endTime')),
      'ipAddress': BackendService.IP // the IP-Address of the backend is stored in the backend service
    };
    // set fields, which will be used in the html file
    this.eventName = sessionStorage.getItem('eventName')!;
    this.passphrase = sessionStorage.getItem('passphrase')!;
    this.qrCodeString = JSON.stringify(qrParameter);
  }

}
