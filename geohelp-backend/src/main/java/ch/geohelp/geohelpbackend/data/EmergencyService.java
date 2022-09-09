package ch.geohelp.geohelpbackend.data;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmergencyService {

    AtomicLong nextID = new AtomicLong();

    EmergencyRepository emergencyRepository = new EmergencyRepository();

    /**
     * adds given event to the emergencyRepository;
     * @param event
     */
    public synchronized void addEvent(Event event){
        emergencyRepository.addEvent(event);
    }

    /**
     * checks if the location(longitude and latitude) of the given emergency is in the predefined eventArea
     * (location in Area) : add given emergency to the emergencyRepository
     * !(location in Area) : do nothing
     * @param emergency
     * @param event
     */
    public synchronized void addEmergency(Emergency emergency, Event event){
        double currLongitude = emergency.longitude;
        double currLatitude = emergency.latitude;


        Event currEvent = event;
        double[] eventLongitude = event.getLongitude();
        double[] eventLatitude = event.getLatitude();

        // here we define the endpoints of a rectangular which represents the eventArea
        double minLongitude = Math.min(eventLongitude[0],eventLongitude[1]) ;
        double minLatitude = Math.min(eventLatitude[0],eventLatitude[1]);
        double maxLongitude = Math.max(eventLongitude[0],eventLongitude[1]) ;
        double maxLatitude = Math.max(eventLatitude[0],eventLatitude[1]);

        // simple checking if the location is in the rectangle or on the border
        if( (currLongitude <= maxLongitude) && (currLongitude >= minLongitude) && (currLatitude <= maxLatitude) && (currLatitude >= minLatitude) ){
            long tmp = nextID.getAndIncrement();
            emergency.emergencyID = tmp;
            emergencyRepository.addEmergency(emergency);
        }


    }

    /**
     *
     * @param emergencyID
     * @param eventID
     * @return an Emergency[] with all emergencies from the emergencyRepository, that posses an emergencyID greater than the given emergencyID
     */
    public synchronized Emergency[] getNewEmergencies(long emergencyID, long eventID){
        return emergencyRepository.getNewEmergencies(emergencyID,eventID);
    }

    /**
     *
     * @param longitude
     * @param latitude
     * @param eventID
     * @return all emergencies around given location in a predefined radius(surroundingParameter)
     */
    public synchronized Emergency[] getEmergenciesNearby(double longitude, double latitude, long eventID){

        //defines the radius in which to look for (here 10m)
        double surroundingParameter = 0.0001;
        ArrayList<Emergency> emergencyArrayList = new ArrayList<>();
        Emergency[] allEmergencies = emergencyRepository.getAllEmergencies(eventID);


        for(Emergency e : allEmergencies){
            double currLongitude = e.getLongitude();
            double currLatitude = e.getLatitude();

        // simple checking if the location of the event is in the predefined radius of given location
            if(Math.abs(currLatitude-latitude) <= surroundingParameter || Math.abs(currLongitude-longitude) <= surroundingParameter){
                if (e.isApproved()) {
                    emergencyArrayList.add(e);
                }
            }
        }

        Emergency[] out = emergencyArrayList.toArray(new Emergency[0]);

        return out;
    }

    /**
     * gets the current emergency from the emergencyRepository using the given eventID and given emergencyID
     * assings the given approved var to the current Emergency
     * assings the given responseMessage var to the current Emergency
     * @param eventID
     * @param emergencyID
     * @param approved
     * @param responseMessage
     */
    public synchronized void approveEmergency(long eventID, long emergencyID, boolean approved, String responseMessage){
        Emergency currEmergency = emergencyRepository.emergencies.get(eventID).get(emergencyID);
        currEmergency.approved = approved;
        currEmergency.responseMessage = responseMessage;
    }

}
