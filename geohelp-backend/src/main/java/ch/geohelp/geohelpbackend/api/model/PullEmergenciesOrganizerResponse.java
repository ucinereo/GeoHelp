package ch.geohelp.geohelpbackend.api.model;

import ch.geohelp.geohelpbackend.data.Emergency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PullEmergenciesOrganizerResponse {
    Emergency[] emergencies;
}
