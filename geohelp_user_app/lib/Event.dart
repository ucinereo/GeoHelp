import 'package:date_format/date_format.dart';

class Event {
  int id = 0;
  String ipAddress = "";
  int startTime = 0;
  int endTime = 0;
  String eventName;
  DateTime startTimeDateTime = DateTime(2000);
  DateTime endTimeDateTime = DateTime(2000);

  Event(this.id, this.ipAddress, this.startTime, this.endTime, this.eventName) {
    startTimeDateTime = convertTimestamp(startTime);
    endTimeDateTime = convertTimestamp(endTime);
  }
  
  /// takes UNIX timestamp (secondsSinceEpoch)
  /// returns DateTime object
  DateTime convertTimestamp(int time) {
    return DateTime.fromMillisecondsSinceEpoch(time * 1000);
  }

  int getId() {
    return id;
  }

  String getIpAddress() {
    return ipAddress;
  }

  int getStartTime() {
    return startTime;
  }

  int getEndTime() {
    return endTime;
  }

  String getName() {
    return eventName;
  }

  DateTime getStartTimeDateTime() {
    return startTimeDateTime;
  }

  DateTime getEndTimeDateTime() {
    return endTimeDateTime;
  }

  String getDate() {
    DateTime date = convertTimestamp(startTime);
    return formatDate(date, [dd, '/', mm, '/', yyyy, ' ', HH, ':', nn]).toString();
  }

  /// returns true if event is active now
  bool active() {
    int timestamp = (DateTime.now().millisecondsSinceEpoch);
    timestamp = timestamp ~/ 1000;
    return (startTime <= timestamp && endTime >= timestamp);
  }

}