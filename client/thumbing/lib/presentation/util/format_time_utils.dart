import 'package:intl/intl.dart';

class FormatTimeUtils{
  static String toTimeString(DateTime dateTime){
    DateFormat formatter = new DateFormat('yyyy-MM-dd hh:mm:ss');
    return formatter.format(dateTime);
  }
}