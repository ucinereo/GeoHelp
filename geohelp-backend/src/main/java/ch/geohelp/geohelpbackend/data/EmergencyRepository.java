package ch.geohelp.geohelpbackend.data;

import java.util.ArrayList;
import java.util.HashMap;

public class EmergencyRepository {
    HashMap<Long, HashMap<Long, Emergency>> emergencies = new HashMap<>();

    /**
     * creates a new HashMap <Long, Emergency>
     * adds the created HashMap to emergencies (eventID : newEmergencyMap)
     * @param event
     */
    public synchronized void addEvent(Event event){
        HashMap<Long, Emergency> newEmergencyMap = new HashMap<>();
        emergencies.put(event.eventID, newEmergencyMap);
    }

    /**
     * gets the emergencyMap of the event of the given emergency
     * adds the given emergency to the correct emergencyMap
     * @param emergency
     */
    public synchronized void addEmergency(Emergency emergency){
        HashMap<Long, Emergency> currEmergencyMap = emergencies.get(emergency.eventID);
        currEmergencyMap.put(emergency.emergencyID, emergency);
    }


    /**
     *
     * @param emergencyID
     * @param eventID
     * @return an Emergency[] with all emergencies from the emergencyRepository,
     * that posses an emergencyID greater than the given emergencyID
     */
    public synchronized Emergency[] getNewEmergencies(long emergencyID, long eventID){
        ArrayList<Emergency> emergencyArrayList = new ArrayList<>();
        HashMap<Long, Emergency> currEmergencyMap = emergencies.get(eventID);

        for(Emergency e : currEmergencyMap.values()){
            if(e.emergencyID > emergencyID){
                emergencyArrayList.add(e);
            }
        }

        Emergency[] out = emergencyArrayList.toArray(new Emergency[0]);

        return out;
    }

    /**
     *
     * @param eventID
     * @return an Emergency[] of all emergencies in the emergencyRepository of the given event
     */
    public synchronized Emergency[] getAllEmergencies(long eventID){

        HashMap<Long, Emergency> currEmergencyMap = emergencies.get(eventID);
        Emergency[] out = currEmergencyMap.values().toArray(new Emergency[0]);

        return out;
    }

}
