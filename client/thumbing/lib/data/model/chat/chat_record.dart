import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class ChatRecord extends Equatable {
  final String id;

  final String message;

  final String howLongBrfore;

  final isSender;

  ChatRecord({this.id, this.message, this.isSender, this.howLongBrfore});

  static ChatRecord getChatRecord(int index) {
    return ChatRecord(
      id: Uuid().v4(),
      message: "我妈喊我回家吃饭",
      isSender: index % 2 == 0,
      howLongBrfore: index == 0 ? "一小时前" : index.toString() + "天前",
    );
  }

  @override
  List<Object> get props => [id, message, isSender, howLongBrfore];
}
