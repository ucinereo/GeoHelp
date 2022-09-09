package ch.geohelp.geohelpbackend.api.controller;

import ch.geohelp.geohelpbackend.api.model.*;
import ch.geohelp.geohelpbackend.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin

public class Controller {

    @Autowired
    EventService eventService;

    @Autowired
    EmergencyService emergencyService;

    /**
     * creates an event taking the variables from the EventRegisterRequest-object
     * adds the event to the EventService-object
     * adds the event to the EmergencyService-object
     * @param eventRegisterRequest
     * @return responseMap which contains an error-field and a data-field,
     * if try successfull: return responseMap with "error : false" and "data : eventCreationResponse"
     * else: return internalServerError and responseMap with "error : true"
     */
    @PostMapping(value = "/register-event")
    public ResponseEntity<?> registerEvent(@RequestBody EventRegisterRequest eventRegisterRequest){
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            long currEventID = -1;
            double[] currLongitude = eventRegisterRequest.getLocationLong();
            double[] currLatitude = eventRegisterRequest.getLocationLat();
            String currPassphrase = "";
            long currStartTime = eventRegisterRequest.getStartTime();
            long currEndTime = eventRegisterRequest.getEndTime();
            String currEventName = eventRegisterRequest.getEventName();

            Event newEvent = new Event(currEventID, currLongitude, currLatitude, currPassphrase, currStartTime,currEndTime,currEventName);
            currPassphrase = eventService.addEvent(newEvent);
            emergencyService.addEvent(newEvent);
            responseMap.put("error",false);
            EventCreationResponse eventCreationResponse = new EventCreationResponse(newEvent);
            responseMap.put("data", eventCreationResponse);
            return ResponseEntity.ok(responseMap);
        } catch(Error | Exception e) {
            responseMap.put("error", true);
            return ResponseEntity.internalServerError().body(responseMap);
        }
    }


    /**
     * returns array of all emergencies with an EmergencyID higher than the given one
     * if given passphrase matches eventPassphrase
     * @param pullEmergenciesOrganizerRequest
     * @return responseMap which contains an error-field and a data-field
     * if passPhrases not equal: return badRequestError and responseMap with "error : true"
     * if try successfull: return responseMap with "error : false" and "data : pullEmergencyOrganizerResponse"
     * else: return internalServerError and responseMap with "error : true"
     */
    @PostMapping(value = "/pull-emergencies")
    public ResponseEntity<?> pullEmergencies(@RequestBody PullEmergenciesOrganizerRequest pullEmergenciesOrganizerRequest){
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            responseMap.put("error",false);
            long currEmergencyID = pullEmergenciesOrganizerRequest.getEmergencyID();
            long currEventID = pullEmergenciesOrganizerRequest.getEventID();

            String passphraseRequest = pullEmergenciesOrganizerRequest.getPassphrase();
            String passphraseEvent = eventService.getEvent(currEventID).getPassphrase();

            if(!passphraseRequest.equals(passphraseEvent)){
                responseMap.put("error", true);
                return ResponseEntity.badRequest().body(responseMap);
            }

            Emergency[] emergencyArr = emergencyService.getNewEmergencies(currEmergencyID, currEventID);
            PullEmergenciesOrganizerResponse pullEmergenciesOrganizerResponse = new PullEmergenciesOrganizerResponse(emergencyArr);
            responseMap.put("data", pullEmergenciesOrganizerResponse);
            return ResponseEntity.ok(responseMap);
        } catch(Error | Exception e) {
            responseMap.put("error", true);
            return ResponseEntity.internalServerError().body(responseMap);
        }

    }

    /**
     * approves given emergency if given Passphrase matches eventPassphrase
     * @param approveEmergencyRequest
     * @return responseMap which contains an error-field
     * if passPhrases not equal: return badRequestError and responseMap with "error : true"
     * if try successfull: return responseMap with "error : false"
     * else: return internalServerError and responseMap with "error : true"
     */
    @PostMapping(value = "/approve-emergency")
    public ResponseEntity<?> approveEmergency(@RequestBody ApproveEmergencyRequest approveEmergencyRequest){
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            responseMap.put("error",false);
            long currEventID = approveEmergencyRequest.getEventID();
            long currEmergencyID = approveEmergencyRequest.getEmergencyID();
            String currResponseMessage = approveEmergencyRequest.getMessage();

            String passphraseRequest = approveEmergencyRequest.getPassphrase();
            String passphraseEvent = eventService.getEvent(currEventID).getPassphrase();

            if(!passphraseRequest.equals(passphraseEvent)){
                responseMap.put("error", true);
                return ResponseEntity.badRequest().body(responseMap);
            }

            emergencyService.approveEmergency(currEventID,currEmergencyID,true,currResponseMessage);
            return ResponseEntity.ok(responseMap);

        } catch(Error | Exception e) {
            responseMap.put("error", true);
            return ResponseEntity.internalServerError().body(responseMap);
        }

    }

    /**
     * searches event which matches given eventName
     * checks given passphrase with eventPassphrase
     * @param loginRequest
     * @return responseMap which contains an error-field and a data-field
     * if passPhrases not equal: return badRequestError and responseMap with "error : true"
     * if try successfull: return responseMap with "error : false" and "data : loginResponse"
     * else: return internalServerError and responseMap with "error : true"
     */
    @PostMapping(value ="/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            responseMap.put("error",false);
            String currEventName = loginRequest.getEventName();
            String passphraseRequest = loginRequest.getPassphrase();

            HashMap<Long, Event> allEvents = eventService.getAllEvents();
            Event currEvent = null;

            for(Event e : allEvents.values()){
                if(e.getEventName().equals(currEventName)){
                    currEvent = e;
                    break;
                }
            }

            long currEventID = currEvent.getEventID();
            String passphraseEvent = currEvent.getPassphrase();

            if(!passphraseRequest.equals(passphraseEvent)){
                responseMap.put("error", true);
                LoginResponse loginResponse = new LoginResponse(false, -1);
                responseMap.put("data", loginResponse);
                return ResponseEntity.badRequest().body(responseMap);
            }

            LoginResponse loginResponse = new LoginResponse(true, currEventID);
            responseMap.put("data", loginResponse);
            return ResponseEntity.ok(responseMap);

        } catch(Error | Exception e) {
            responseMap.put("error", true);
            return ResponseEntity.internalServerError().body(responseMap);
        }

    }

    /**
     * creates an emergency taking the variables from the EmergencyTriggerRequest-object
     * adds the emergency to the EmergencyService-object
     * @param emergencyTriggerRequest
     * @return responseMap which contains an error-field or nothing
     * if try successfull: return empty responseMap
     * else: return internalServerError and responseMap with "error : true"
     */
    @PostMapping(value ="/trigger-emergency")
    public ResponseEntity<?> triggerEmergency(@RequestBody EmergencyTriggerRequest emergencyTriggerRequest){

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            long currEventID = emergencyTriggerRequest.getEventID();;
            long currEmergencyID = -1;
            boolean currApproved = false;
            boolean currAccepted = false;
            String currResponseMessage = "";
            double currLongitude = emergencyTriggerRequest.getLongitude();
            double currLatitude = emergencyTriggerRequest.getLatitude();
            Event currEvent = eventService.getEvent(currEventID);

            Emergency emergency = new Emergency(currEventID, currEmergencyID, currApproved, currAccepted, currResponseMessage, currLongitude, currLatitude);
            emergencyService.addEmergency(emergency, currEvent);

            return ResponseEntity.ok(responseMap);

        } catch(Error | Exception e) {
            responseMap.put("error", true);
            return ResponseEntity.internalServerError().body(responseMap);
        }


    }

    /**
     * checks if there are any emergencies near the callers location
     * @param pullEmergenciesClientRequest
     * @return responseMap which contains an error-field and a data-field
     * if try successfull:
     * emergencies nearby: return responseMap with "error : false" and "data : pullEmergenciesClientResponse"
     * with pullEmergenciesClientResponse.exists = true
     * !(emergencies nearby): return responseMap with "error : false" and "data : pullEmergenciesClientResponse"
     * with pullEmergenciesClientResponse.exists = false
     * else: return internalServerError and responseMap with "error : true"
     */
    @PostMapping(value="/pull-emergency-state")
    public ResponseEntity<?> pullEmergencyState(@RequestBody PullEmergenciesClientRequest pullEmergenciesClientRequest){

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            long currEventID = pullEmergenciesClientRequest.getEventID();
            double currLongitude = pullEmergenciesClientRequest.getLongitude();
            double currLatitude = pullEmergenciesClientRequest.getLatitude();
            responseMap.put("error",false);

            Emergency[] emergencies = emergencyService.getEmergenciesNearby(currLongitude, currLatitude, currEventID);
            boolean currExists = false;
            boolean currApproved = false;
            String currResponseMessage = "";

            if(emergencies.length == 0){
                currExists = false;
                PullEmergenciesClientResponse pullEmergenciesClientResponse = new PullEmergenciesClientResponse(currExists, currApproved,currResponseMessage);
                responseMap.put("data", pullEmergenciesClientResponse);
                return ResponseEntity.ok(responseMap);
            }

            currExists = true;
            currApproved = emergencies[0].isApproved();
            currResponseMessage = emergencies[0].getResponseMessage();
            PullEmergenciesClientResponse pullEmergenciesClientResponse = new PullEmergenciesClientResponse(currExists, currApproved,currResponseMessage);

            responseMap.put("data", pullEmergenciesClientResponse);
            return ResponseEntity.ok(responseMap);

        } catch(Error | Exception e) {
            responseMap.put("error", true);
            return ResponseEntity.internalServerError().body(responseMap);
        }

    }

}
