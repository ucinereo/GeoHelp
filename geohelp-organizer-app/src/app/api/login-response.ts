export class LoginResponse {

  public success: boolean;
  public eventID: number;


  constructor(success: boolean, eventID: number) {
    this.success = success;
    this.eventID = eventID;
  }

}
