import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Emergency} from "../api/emergency";
import {PullEmergenciesOrganizerRequest} from "../api/pull-emergencies-organizer-request";
import {BackendService} from "../backend.service";
import {PullEmergenciesOrganizerResponse} from "../api/pull-emergencies-organizer-response";
import {HeatmapComponent} from "../heatmap/heatmap.component";
import {interval, Subscription} from "rxjs";
import {ApproveEmergencyRequest} from "../api/approve-emergency-request";

@Component({
  selector: 'app-event-monitoring',
  templateUrl: './event-monitoring.component.html',
  styleUrls: ['./event-monitoring.component.scss']
})
export class EventMonitoringComponent implements OnInit, OnDestroy {

  // the heatmap component
  @ViewChild('map')
  private map!: HeatmapComponent;

  // list of all emergencies
  public emergencies: Emergency[] = [];

  // id and passphrase of the current event
  private eventID: number = -1;
  public passphrase: string = "";

  // store the subscription reference to unsubscribe on destroy
  private subscription!: Subscription;

  constructor(private backendService: BackendService) { }

  ngOnInit(): void {
    // get the passphrase and eventID from the session storage
    this.eventID = Number(sessionStorage.getItem('eventID'));
    this.passphrase = sessionStorage.getItem('passphrase')!;

    // create an interval to poll for new emergencies every second
    const source = interval(1000);
    this.subscription = source.subscribe(() => this.pullNewEmergencies());

    this.pullNewEmergencies();
  }

  public pullNewEmergencies(): void {
    // get the latest emergency id, (only newer emergencies will be sent by the backend)
    // if no emergencies exist at this point, use -1 then all emergencies will be sent by the backend
    let latestID = -1;
    if (this.emergencies.length > 0) {
      latestID = this.emergencies[this.emergencies.length -1].emergencyID;
    }

    // create the request object, using the passphrase, which will be used for authentication, the eventID and latest emergencyID
    const request: PullEmergenciesOrganizerRequest = new PullEmergenciesOrganizerRequest(this.eventID, this.passphrase, latestID);

    // request the new emergencies from the backend
    this.backendService.pullEmergencies(request).subscribe((response) => {
      const data: PullEmergenciesOrganizerResponse = response.data;
      for (let e of data.emergencies) {
        // add the new emergencies to the emergency list
        this.emergencies.push(e);
        // plot the new emergencies on the heatmap
        this.map.add(e.longitude, e.latitude);
      }
    })

  }

  // will be called when the organizer clicks on the map to approve en emergency
  public onApprove(event: any) {
    if (this.emergencies.length > 0) {
      // get the coordinates of the click event
      const lng = event.latlng.lng;
      const lat = event.latlng.lat;

      // find the closest emergency, by comparing the distance between the click event and each emergency
      let min: Emergency = this.emergencies[0];
      let dist = Math.sqrt(Math.pow(min.longitude - lng, 2) + Math.pow(min.latitude - lat, 2));
      for (let e of this.emergencies) {
        const d = Math.sqrt(Math.pow(e.longitude - lng, 2) + Math.pow(e.latitude - lat, 2));
        if (d < dist) {
          min = e;
          dist = Math.sqrt(Math.pow(min.longitude - lng, 2) + Math.pow(min.latitude - lat, 2));
        }
      }

      // send a approveEmergencyRequest to the backend to approve the emergency found above
      let request: ApproveEmergencyRequest = new ApproveEmergencyRequest(this.eventID, this.passphrase, min.emergencyID, "Help is on the way", true);
      this.backendService.approveEmergency(request).subscribe((response) => { })
    }
  }

  ngOnDestroy(): void {
    // unsubscribe from the emergency poll subscription
    this.subscription.unsubscribe();
  }

}
