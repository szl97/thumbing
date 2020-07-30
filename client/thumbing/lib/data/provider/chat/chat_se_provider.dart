import 'dart:async';

import 'package:thumbing/data/model/chat/chat_session.dart';

class ChatSessionProvider {
  Future<List<ChatSession>> getChatRecords() async {
    var list = List.generate(10, (index) => ChatSession.getChatSession(index));
    return Future.delayed(const Duration(milliseconds: 300), () => list);
  }
}
