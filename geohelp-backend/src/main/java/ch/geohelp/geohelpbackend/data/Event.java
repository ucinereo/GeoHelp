package ch.geohelp.geohelpbackend.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Event {
    long eventID;
    double[] longitude;
    double[] latitude;
    String passphrase;
    long startTime;
    long endTime;
    String eventName;
}
