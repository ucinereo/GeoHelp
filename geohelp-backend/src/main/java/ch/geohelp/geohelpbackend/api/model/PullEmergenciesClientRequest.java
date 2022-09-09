package ch.geohelp.geohelpbackend.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PullEmergenciesClientRequest {
    long eventID;
    double longitude;
    double latitude;
}






