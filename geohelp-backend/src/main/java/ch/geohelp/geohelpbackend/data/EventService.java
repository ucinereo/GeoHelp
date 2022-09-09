package ch.geohelp.geohelpbackend.data;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventService {
    AtomicLong nextID = new AtomicLong();
    EventRepository eventRepository = new EventRepository();

    /**
     * @param eventID
     * @return event which matches the given eventID
     */
    public synchronized Event getEvent(long eventID){
        return eventRepository.getEvent(eventID);
    }

    /**
     * assigns an unique eventID to the event
     * assigns an unique passphrase to the event
     * adds the event to the eventRepository
     * @param event
     * @return unique passphrase of the given event
     */
    public synchronized String addEvent(Event event){
        long tmp = nextID.getAndIncrement();
        event.eventID = tmp;
        event.passphrase = "" + ((int)(Math.random() * 10)) + ((int)(Math.random() * 10)) + ((int)(Math.random() * 10)) + ((int)(Math.random() * 10)) + "";
        eventRepository.addEvent(event);
        return event.passphrase;
    }

    /**
     * gets all events which are currently in the eventRepository
     * @return Hashmap<Long,Event> (eventID : event) containing all events
     */
    public synchronized HashMap<Long,Event> getAllEvents(){
        return eventRepository.getAllEvents();
    }
}
