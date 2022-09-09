export class ApproveEmergencyRequest {

  public eventID: number;
  public passphrase: string;
  public emergencyID: number;
  public message: string;
  public approved: boolean;


  constructor(eventID: number, passphrase: string, emergencyID: number, message: string, approved: boolean) {
    this.eventID = eventID;
    this.passphrase = passphrase;
    this.emergencyID = emergencyID;
    this.message = message;
    this.approved = approved;
  }

}
