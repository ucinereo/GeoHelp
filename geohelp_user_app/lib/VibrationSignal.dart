import 'package:vibration/vibration.dart';

class VibrationSignal {
  static Vibration lastVibration = Vibration();
  static bool vibrate = true;

  static void start() {
    vibrate = true;
    doVibrate();
  }

  static void stop() {
    vibrate = false;
    Vibration.cancel();
  }

  static void doVibrate() async {
    while(vibrate) {
      await Vibration.vibrate();
    }
  }

}