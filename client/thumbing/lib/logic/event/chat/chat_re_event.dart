import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';
import 'package:thumbing/data/model/chat/chat_record.dart';

abstract class ChatRecordEvent extends Equatable {
  const ChatRecordEvent();
  @override
  List<Object> get props => [];
}

class ChatRecordFetched extends ChatRecordEvent {
  const ChatRecordFetched({@required this.sessionId}) : super();
  final String sessionId;
  List<Object> get props => [sessionId];
}

class ChatRecordAdd extends ChatRecordEvent {
  final String sessionId;
  final ChatRecord chatRecord;
  const ChatRecordAdd({@required this.sessionId, @required this.chatRecord})
      : super();
  @override
  List<Object> get props => [sessionId, chatRecord];
}
