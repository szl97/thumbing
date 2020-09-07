import 'package:intl/intl.dart';

class FormatTimeUtils{
  static String toTimeString(DateTime dateTime){
    DateFormat formatter = new DateFormat('yyyy-MM-dd');
    return formatter.format(dateTime);
  }
}