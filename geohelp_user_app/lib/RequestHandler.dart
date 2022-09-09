import 'dart:convert';
import 'package:geolocator/geolocator.dart';
import 'package:http/http.dart' as http;
import 'Event.dart';

class RequestHandler {

  /// Sends trigger-emergency request to server (ip_address specified by event object)
  /// requires callback function with parameter map that gets response data when called
  Future<void> triggerEmergency(Event event, Function callback) async {
    await getLocationPermissions();
    Position position = await Geolocator.getCurrentPosition(desiredAccuracy: LocationAccuracy.high);
    print("trigger-emergency (${position.latitude}, ${position.longitude})");
    print("${event.getIpAddress()}trigger-emergency");
    var response = await http.post(
        Uri.parse("${event.getIpAddress()}trigger-emergency"),
        headers: {
          "content-type" : "application/json",
          "accept" : "application/json"
        },
        body: json.encode({
          "eventID" : event.getId(),
          "longitude": position.longitude,
          "latitude": position.latitude
        })
    );

    // Map responseData = jsonDecode(response.body);
    // call callback with Map of response data (JSON)
    callback(jsonDecode(response.body));
  }

  /// Sends trigger-emergency request to server (ip_address specified by event object)
  /// requires callback function with parameter map that gets response data when called
  Future<Map> pullEmergencyState(Event event) async {
    await getLocationPermissions();
    Position position = await Geolocator.getCurrentPosition(desiredAccuracy: LocationAccuracy.high);
    print("pull-emergency-state (${position.longitude}, ${position.latitude})");
    print("${event.getIpAddress()}pull-emergency-state");
    var response = await http.post(
        Uri.parse("${event.getIpAddress()}pull-emergency-state"),
        headers: {
          "content-type" : "application/json",
          "accept" : "application/json"
        },
        body: json.encode({
          "eventID" : event.getId(),
          "longitude": position.longitude,
          "latitude": position.latitude
        })
    );

    // Map responseData = jsonDecode(response.body);
    // call callback with Map of response data (JSON)
    print(response.body);
    return jsonDecode(response.body);
  }

  Future<void> getLocationPermissions() async {
    await Geolocator.isLocationServiceEnabled();
    LocationPermission permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        print('Location permissions are denied');
      }else if(permission == LocationPermission.deniedForever){
        print("'Location permissions are permanently denied");
      }else{
        print("GPS Location service is granted");
      }
    }else{
      print("GPS Location permission granted.");
    }
  }
}