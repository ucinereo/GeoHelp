package ch.geohelp.geohelpbackend.api.model;


import ch.geohelp.geohelpbackend.data.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EventCreationResponse {
    Event event;
}
