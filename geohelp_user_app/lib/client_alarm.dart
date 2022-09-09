import 'package:flutter/material.dart';
import 'package:geohelp_user_app/VibrationSignal.dart';
import 'package:geohelp_user_app/main.dart';
import 'package:vibration/vibration.dart';

/// ClientAlarmPage object represents the client_alarm_view
class ClientAlarmPage extends StatefulWidget {
  Map _config = {};
  ValueChanged<String> _changePage = (String s){};

  ClientAlarmPage(config, changePage, {Key? key}) : super(key: key) {
    _config = config;
    _changePage = changePage;
  }
  @override
  ClientAlarmPageState createState() => ClientAlarmPageState();
}

class ClientAlarmPageState extends State<ClientAlarmPage> {
  // initial color of stop_vibration_button
  Color _buttonColor = createMaterialColor(const Color(0xffe056fd));
  @override
  void initState(){
    super.initState();
  }

  @override
  Widget build(BuildContext context) => Scaffold(
    body: Container(
        alignment: Alignment.center,
        child: Center(
          // stop_vibration_button
          child: OutlinedButton(
              style: OutlinedButton.styleFrom(
                  elevation: 6,
                  padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 50),
                  backgroundColor: _buttonColor,
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(150)
                  )
              ),
              child: const Icon(IconData(0xe6c4, fontFamily: 'MaterialIcons'), size: 200, color: Colors.white),
              // onPress event stop_vibration_button
              onPressed: () {
                // change color of stop_vibration_button
                _buttonColor = createMaterialColor(const Color(0xffbe2edd));
                VibrationSignal.stop();
                setState((){});
              }
          ),
        ),
      ),
  );
}