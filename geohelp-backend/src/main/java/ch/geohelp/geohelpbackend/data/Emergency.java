package ch.geohelp.geohelpbackend.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Emergency {
    long eventID;
    long emergencyID;
    boolean approved;
    boolean accepted;
    String responseMessage;
    double longitude;
    double latitude;
}
