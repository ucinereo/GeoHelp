import 'dart:convert';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:geohelp_user_app/VibrationSignal.dart';
import 'package:geohelp_user_app/main.dart';
import 'package:geolocator/geolocator.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:android_alarm_manager_plus/android_alarm_manager_plus.dart';
import 'Event.dart';
import 'package:qrscan/qrscan.dart' as scanner;

/// ClientLoginPage object represents the client_login_view
class ClientLoginPage extends StatefulWidget {
  Map _config = {};
  ValueChanged<String> _changePage = (String s){};
  VoidCallback _callback = (){};

  ClientLoginPage(config, changePage, changePageCallback, {Key? key}) : super(key: key) {
    _config = config;
    _changePage = changePage;
    _callback = changePageCallback;
  }
  @override
  ClientLoginPageStage createState() => ClientLoginPageStage();
}



class ClientLoginPageStage extends State<ClientLoginPage> {
  // initial value for scanned data
  String result = "no_data_scanned";

  /// starts QRCode-Scanner and adds new event to events list when recognised
  Future _scanQR() async {
    try {
      // check if required permissions are set, otherwise request access from user
      await Permission.camera.request();
      await Geolocator.requestPermission();
      await Geolocator.requestPermission();
      // start QR scanner
      String? cameraScanResult = await scanner.scan();
      // decode JSON string stored in the QR code
      Map qrData = jsonDecode(cameraScanResult.toString());
      // construct new event object with values from QR code data
      Event event = Event(qrData["eventId"], qrData["ipAddress"], qrData["startTime"], qrData["endTime"], qrData["eventName"]);
      // add event object to global event list
      widget._config["events"].add(event);
      // start background process to join event when event starts (defined by EventStartTime)
      addEventAlarm(event);
      setState(() { });
    } on PlatformException catch (e) {
      print("error_add_event");
    }
  }

  /// add new EventAlarm to AlarmManager of Android OS (process running in the background even if phone is locked)
  /// if eventStartTime is reached client_event_view is loaded by background-process and global activeEvent is set.
  void addEventAlarm(Event event) async {
    WidgetsFlutterBinding.ensureInitialized();
    await AndroidAlarmManager.initialize();
    final int alarmId = Random().nextInt(pow(2, 31) as int);
    await AndroidAlarmManager.oneShotAt(event.getStartTimeDateTime(), alarmId, widget._callback, exact: true, wakeup: true);
  }

  @override
  void initState(){
    super.initState();
  }

  @override
  Widget build(BuildContext context) => Scaffold(
    body: Center(
      child: ListView(
        children: <Widget>[
        Padding(
          padding: const EdgeInsets.only(left: 20, top: 40, right: 20),
          child: Container(
            alignment: Alignment.center,
            color: createMaterialColor(const Color(0xFFbe2edd)),
              // table listing known events (events added by QR code)
              child: Table(
                border: const TableBorder(horizontalInside: BorderSide(width: 1, color: Colors.black, style: BorderStyle.solid)),
                columnWidths: const <int, TableColumnWidth>{
                  0: FlexColumnWidth(),
                  1: FlexColumnWidth(),
                  2: FixedColumnWidth(64),
                },
                defaultVerticalAlignment: TableCellVerticalAlignment.middle,
                  // dynamically build table
                children: _buildTable(widget._config['events'])
              ),
            ),
        ),
          Center(
            child: Padding(
              padding: const EdgeInsets.only(top: 40),
              // add_event_button
              child: OutlinedButton(
                style: OutlinedButton.styleFrom(
                  elevation: 6,
                  padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 20),
                  backgroundColor: createMaterialColor(const Color(0xffe056fd)),
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(150)
                  )
                ),
                child: const Icon(IconData(0xe04b, fontFamily: 'MaterialIcons'), size: 100, color: Colors.white),
                // onPressed event add_event_button
                onPressed: () {
                  // open QR scanner
                  _scanQR();
                },
              ),
            ),
          ),
        ],
      ),
    ),
  );

  /// takes global event list as input and returns list of TableRow objects,
  /// that can be directly includes as children of a Table widget
  List <TableRow> _buildTable(events) {
    List<TableRow> rows = [];
    try {
      for(Event e in events) {
        rows.add(
          // build TableRow object
          TableRow(
            children: <Widget>[
              TableCell(
                verticalAlignment: TableCellVerticalAlignment.middle,
                child: SizedBox(
                  height: 20,
                  width: 32,
                  child: Text("   ${e.getName()}", style: const TextStyle(color: Colors.white, fontSize: 16.0, fontWeight: FontWeight.bold)),
                )
              ),
              TableCell(
                verticalAlignment: TableCellVerticalAlignment.middle,
                child: SizedBox(
                  height: 20,
                  width: 32,
                  child: Text(e.getDate(), style: const TextStyle(color: Colors.white, fontSize: 16.0)),
                )
              ),
              TableCell(
                verticalAlignment: TableCellVerticalAlignment.middle,
                child: IconButton(
                  padding: const EdgeInsets.only(bottom: 2),
                  icon: const Icon(IconData(0xe1b9, fontFamily: 'MaterialIcons'), size: 40.0),
                  iconSize: 5,
                  color: Colors.white,
                  tooltip: 'delete_event',
                  onPressed: () {
                    widget._config['events'].remove(e);
                    setState(() {});
                  },
                ),
              ),
            ],
          ),
        );
      }
    } catch(e) {
      debugPrint(e.toString());
    }
    return rows;
  }
}