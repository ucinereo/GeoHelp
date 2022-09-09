export class EventRegisterRequest {

  public startTime: number = 0;
  public endTime: number = 0;
  public eventName: string = "";
  public locationLong: number[];
  public locationLat: number[];

  constructor(startTime: number, endTime: number, eventName: string, locationLong: number[], locationLat: number[]) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.eventName = eventName;
    this.locationLong = locationLong;
    this.locationLat = locationLat;
  }

}
