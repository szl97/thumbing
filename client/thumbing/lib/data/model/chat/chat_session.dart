import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/user/nick_user.dart';
import 'package:uuid/uuid.dart';

class ChatSession extends Equatable {
  final String id;
  /*
     * 用户id
     */
  final NickUser users;
  /*
     * 最后一条消息的内容
     */
  final String lastMessage;
  /*
     * 最后一条消息的时间
     */
  final DateTime lastTime;
  /*
     * 最后一条消息的时间
     */
  final String howLongBrfore;
  /*
     * 是否已读
     */
  final bool read;

  final Set<String> userIds;

  ChatSession(
      {this.id,
      this.users,
      this.howLongBrfore,
      this.lastMessage,
      this.lastTime,
      this.read,
      this.userIds});

  static ChatSession getChatSession(int index) {
    return ChatSession(
      id: Uuid().v4(),
      users: NickUser(nickName: "好友" + index.toString() + "号"),
      lastMessage: "我妈喊我回家吃饭",
      howLongBrfore: index == 0 ? "一小时前" : index.toString() + "天前",
      read: index == 0 ? false : true,
      userIds: Set()..add("123456"),
    );
  }

  @override
  List<Object> get props =>
      [id, users, howLongBrfore, lastMessage, lastTime, read, userIds];
}
