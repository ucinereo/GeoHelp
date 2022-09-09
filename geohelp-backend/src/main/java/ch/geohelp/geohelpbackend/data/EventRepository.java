package ch.geohelp.geohelpbackend.data;

import java.util.HashMap;

public class EventRepository {
    HashMap<Long, Event> events = new HashMap<Long, Event>();

    /**
     *
     * @param eventID
     * @return event with the given eventID
     */
    public synchronized Event getEvent(long eventID){
        return events.get(eventID);
    }

    /**
     * adds given event to the events-hashmap (eventID : event)
     * @param event
     */
    public synchronized void addEvent(Event event){
        events.put(event.eventID, event);
    }

    /**
     *
     * @return HashMap<Long, Event> of all events (eventID : event)
     */
    public HashMap<Long, Event> getAllEvents() {
        return events;
    }
}
