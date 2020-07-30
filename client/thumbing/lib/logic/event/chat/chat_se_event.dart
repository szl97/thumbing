import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/chat/chat_session.dart';

abstract class ChatSessionEvent extends Equatable {
  const ChatSessionEvent();

  @override
  List<Object> get props => [];
}

class ChatSessionFetched extends ChatSessionEvent {}

class ChatSessionAdd extends ChatSessionEvent {
  final ChatSession chatSession;
  const ChatSessionAdd({this.chatSession}) : super();
  @override
  List<Object> get props => [chatSession];
}
