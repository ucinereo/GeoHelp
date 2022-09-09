import 'dart:io';
import 'dart:isolate';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:geohelp_user_app/RequestHandler.dart';
import 'package:geohelp_user_app/VibrationSignal.dart';
import 'package:geohelp_user_app/client_login.dart';
import 'client_alarm.dart';
import 'client_event.dart';
import 'Event.dart';

/// takes Color object, return MaterialColor object
MaterialColor createMaterialColor(Color color) {
  List strengths = <double>[.05];
  Map<int, Color> swatch = {};
  final int r = color.red, g = color.green, b = color.blue;

  for (int i = 1; i < 10; i++) {
    strengths.add(0.1 * i);
  }
  for (var strength in strengths) {
    final double ds = 0.5 - strength;
    swatch[(strength * 1000).round()] = Color.fromRGBO(
      r + ((ds < 0 ? r : (255 - r)) * ds).round(),
      g + ((ds < 0 ? g : (255 - g)) * ds).round(),
      b + ((ds < 0 ? b : (255 - b)) * ds).round(),
      1,
    );
  }
  return MaterialColor(color.value, swatch);
}

// main config holds values shared between client_views (StatefulWidget)
final Map config = {
  // FRAME
  'pageId': 0,
  // EVENTS
  'events': [],
  'activeEvent': Event(0, "10.5.95.155:8080", 0, 0, "testevent"),
  // ORGANIZER
  'organizerFeedback': "default_feedback"
};

// pages defines enumeration of client_views
Map pages = {
  'client_login': 0,
  'client_event': 1,
  'client_alarm': 2,
};

// The [SharedPreferences] key to access the alarm fire count.
const String alarmKey = 'alarm';
// the name associated with the UI isolate's [SendPort]
const String isolateName = 'isolate';
// A port used to communicate from a background isolate to the UI isolate
final ReceivePort port = ReceivePort();

Future<void> main() async {
  IsolateNameServer.registerPortWithName(port.sendPort, isolateName);
  runApp(const MainPage());
}

// global style configuration
final ThemeData themeData = ThemeData(
  canvasColor: createMaterialColor(const Color(0xFF242424)),
  primarySwatch: createMaterialColor(const Color(0xFFbe2edd)),
);

class MainPage extends StatefulWidget {
  const MainPage({Key? key}) : super(key: key);

  @override
  MainPageState createState() => MainPageState();
}

class MainPageState extends State<MainPage> {

  static SendPort? uiSendPort;

  @override
  void initState() {
    super.initState();
    port.listen((_) async => await _changeToEventPage());
  }

  /// changePage includes the selected client_view as body into the main Widget
  void changePage(String page) {
    config['pageId'] = pages[page];
    setState(() {});
  }

  /// callback function to change main view to client_event_view
  Future<void> _changeToEventPage() async {
    config['pageId'] = pages['client_event'];
    // loop through global event list and select activeEvent by time
    for (Event event in config['events']) {
      if (event.active()) {
        config['activeEvent'] = event;
      }
    }
    setState(() {});
    _checkForEmergency();
  }

  /// call asynchronous HTTP request to check if there is an active emergency next to your location
  /// related to your active event
  void _checkForEmergency() async {
    bool eventTime = true;
    do {
      Map response = await RequestHandler().pullEmergencyState(config['activeEvent']);
      var data = response['data'];
      if (data['exists']) {
        config['organizerFeedback'] = data['responseMessage'];
        config['pageId'] = pages['client_alarm'];
        setState(() {});
        VibrationSignal.start();
        break;
      }
      eventTime = config['activeEvent'].active();
      sleep(const Duration(seconds: 5));
    } while(eventTime);
  }

  /// function called by background process to change client_view
  static Future<void> firePageEvent() async {
    uiSendPort ??= IsolateNameServer.lookupPortByName(isolateName);
    uiSendPort?.send(null);
  }

  @override
  Widget build(BuildContext context) {

    // available client_views enumerated by pages
    final screens = [
      ClientLoginPage(config, changePage, firePageEvent),
      ClientEventPage(config, changePage),
      ClientAlarmPage(config, changePage),
    ];

    // main component
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: themeData,
      home: Scaffold(
        appBar: AppBar(
          title: Text('GeoHelp'),
        ),
        body: screens[config['pageId']],
      ),
    );
  }


}