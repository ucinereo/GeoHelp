import 'package:flutter/material.dart';
import 'package:geohelp_user_app/main.dart';
import 'RequestHandler.dart';

/// ClientEventPage object represents the client_event_view
class ClientEventPage extends StatefulWidget {
  Map _config = {};
  ValueChanged<String> _changePage = (String s){};

  ClientEventPage(config, changePage, {Key? key}) : super(key: key) {
    _config = config;
    _changePage = changePage;
  }
  @override
  ClientEventPageStage createState() => ClientEventPageStage();
}

class ClientEventPageStage extends State<ClientEventPage> {
  bool _alarmSent = false;
  IconData _sentIcon = const IconData(0xf0794, fontFamily: 'MaterialIcons');
  Color _sentColor = createMaterialColor(const Color(0xffe056fd));

  @override
  void initState(){
    super.initState();
  }

  @override
  Widget build(BuildContext context) => Scaffold(
    body: Container(
      alignment: Alignment.center,
        child: Center(
          // report_emergency_button
          child: OutlinedButton(
            style: OutlinedButton.styleFrom(
              elevation: 6,
              padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 50),
              backgroundColor: _sentColor,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(150)
              )
            ),
            child: Icon(_sentIcon, size: 200, color: Colors.white),
            // onPress event report_emergency_button
            onPressed: () {
              if(!_alarmSent) {
                // debug feature check if activeEvent is set
                if(widget._config["activeEvent"] != Null) {
                  _alarmSent = true;
                  // change Color of report_emergency_button to give feedback to user, that emergency has been reported
                  _sentColor = createMaterialColor(const Color(0xffbe2edd));
                  // change Icon of report_emergency_button to give feedback to user, that emergency has been reported
                  _sentIcon = const IconData(0xe187, fontFamily: 'MaterialIcons');
                  setState(() { });
                  // call HTTP request to IP-Address of geohelp-backend (ipAddress specified in QR-Code generated by geohelp-organizer-app)
                  RequestHandler().triggerEmergency(widget._config["activeEvent"], (Map responseData) => {});
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                    content: Text("DEBUG: no active event selected", style: TextStyle(color: Colors.red))));
                }
              }
            }
          ),
        ),
      ),
  );
}