export class LoginRequest {

  public eventName: string;
  public passphrase: string;


  constructor(eventName: string, passphrase: string) {
    this.eventName = eventName;
    this.passphrase = passphrase;
  }

}
