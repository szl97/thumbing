import 'dart:async';
import 'package:thumbing/data/model/chat/chat_record.dart';
import 'package:thumbing/data/provider/chat/chat_re_provider.dart';

class ChatRecordRepository {
  ChatRecordProvider chatRecordProvider;
  ChatRecordRepository() {
    chatRecordProvider = ChatRecordProvider();
  }
  Future<List<ChatRecord>> getChatRecords(String sessionId) async {
    return await chatRecordProvider.getChatRecords(sessionId);
  }
}
