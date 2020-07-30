import 'dart:async';
import 'package:thumbing/data/model/chat/chat_session.dart';
import 'package:thumbing/data/provider/chat/chat_se_provider.dart';

class ChatSessionRepository {
  ChatSessionProvider chatSessionProvider;
  ChatSessionRepository() {
    chatSessionProvider = ChatSessionProvider();
  }
  Future<List<ChatSession>> getChatSessions() async {
    return await chatSessionProvider.getChatRecords();
  }
}
