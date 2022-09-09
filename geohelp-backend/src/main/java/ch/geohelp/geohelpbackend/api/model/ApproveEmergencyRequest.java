package ch.geohelp.geohelpbackend.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class ApproveEmergencyRequest {
    long eventID;
    String passphrase;
    long emergencyID;
    String message;
    boolean approved;
}
