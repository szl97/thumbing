import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class ChatRecord extends Equatable {
  final String id;

  final String message;

  final isSender;

  ChatRecord({this.id, this.message, this.isSender});

  static ChatRecord getChatRecord(int index) {
    return ChatRecord(
      id: Uuid().v4(),
      message: "我妈喊我回家吃饭",
      isSender: index % 2 == 0,
    );
  }

  @override
  List<Object> get props => [id, message];
}
