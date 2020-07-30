import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/chat/chat_record.dart';

abstract class ChatRecordEvent extends Equatable {
  const ChatRecordEvent({this.sessionId});
  final String sessionId;

  @override
  List<Object> get props => [sessionId];
}

class ChatRecordFetched extends ChatRecordEvent {}

class ChatRecordAdd extends ChatRecordEvent {
  final ChatRecord chatRecord;
  const ChatRecordAdd({this.chatRecord}) : super();
  @override
  List<Object> get props => [sessionId, chatRecord];
}
