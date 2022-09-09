export class Emergency {

  public eventID: number;
  public emergencyID: number;
  public approved: boolean;
  public accepted: boolean;
  public responseMessage: string;
  public longitude: number;
  public latitude: number;


  constructor(eventID: number, emergencyID: number, approved: boolean, accepted: boolean, responseMessage: string, longitude: number, latitude: number) {
    this.eventID = eventID;
    this.emergencyID = emergencyID;
    this.approved = approved;
    this.accepted = accepted;
    this.responseMessage = responseMessage;
    this.longitude = longitude;
    this.latitude = latitude;
  }

}
