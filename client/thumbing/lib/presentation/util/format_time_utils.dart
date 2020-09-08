import 'package:intl/intl.dart';

class FormatTimeUtils{
  static String toTimeString(DateTime dateTime){
    DateFormat formatter = new DateFormat('yyyy-MM-dd hh:mm:ss');
    return formatter.format(dateTime);
  }

  static String toDescriptionString(DateTime dateTime){
    Duration difference = DateTime.now().difference(dateTime);
    if(difference.inDays > 365){
      return ((difference.inDays / 365).floor()).toString() + '年前';
    } else if(difference.inDays > 31){
      return ((difference.inDays / 31).floor()).toString() + '月前';
    } else if(difference.inDays > 1) {
      return (difference.inDays).toString() + '天前';
    } else if(difference.inDays == 1) {
      return '昨天'.toString();
    } else if(difference.inHours >= 1 && difference.inHours < 24) {
      return (difference.inHours).toString() + '小时前';
    } else if(difference.inMinutes > 2 && difference.inMinutes < 60) {
      return (difference.inMinutes).toString() + '分钟前';
    } else{
      return '刚刚';
    }
  }
}