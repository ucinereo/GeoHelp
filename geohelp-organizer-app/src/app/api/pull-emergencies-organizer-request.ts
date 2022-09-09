export class PullEmergenciesOrganizerRequest {

  public eventID: number;
  public passphrase: string;
  public emergencyID: number;


  constructor(eventID: number, passphrase: string, emergencyID: number) {
    this.eventID = eventID;
    this.passphrase = passphrase;
    this.emergencyID = emergencyID;
  }
}
