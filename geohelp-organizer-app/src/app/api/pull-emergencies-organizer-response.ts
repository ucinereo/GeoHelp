import {Emergency} from "./emergency";

export class PullEmergenciesOrganizerResponse {

  public emergencies: Emergency[];

  constructor(emergencies: Emergency[]) {
    this.emergencies = emergencies;
  }

}
