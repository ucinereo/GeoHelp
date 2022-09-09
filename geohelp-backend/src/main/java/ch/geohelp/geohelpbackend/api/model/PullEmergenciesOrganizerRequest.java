package ch.geohelp.geohelpbackend.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PullEmergenciesOrganizerRequest {
    long eventID;
    String passphrase;
    long emergencyID;
}
