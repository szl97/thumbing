import 'dart:async';
import 'package:thumbing/data/model/chat/chat_record.dart';

class ChatRecordProvider {
  Future<List<ChatRecord>> getChatRecords(String sessionId) async {
    var list = List.generate(10, (index) => ChatRecord.getChatRecord(index));
    return Future.delayed(const Duration(milliseconds: 300), () => list);
  }
}
