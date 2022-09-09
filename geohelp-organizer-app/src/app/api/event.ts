export class Event {

  public eventID: number;
  public longitude: number[];
  public latitude: number[];
  public passphrase: string;
  public startTime: number;
  public endTime: number;
  public eventName: string;


  constructor(eventID: number, longitude: number[], latitude: number[], passphrase: string, startTime: number, endTime: number, eventName: string) {
    this.eventID = eventID;
    this.longitude = longitude;
    this.latitude = latitude;
    this.passphrase = passphrase;
    this.startTime = startTime;
    this.endTime = endTime;
    this.eventName = eventName;
  }
}
