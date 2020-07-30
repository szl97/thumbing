import 'package:badges/badges.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/chat/chat_session.dart';
import 'package:thumbing/logic/bloc/chat/chat_se_bloc.dart';
import 'package:thumbing/logic/event/chat/chat_se_event.dart';
import 'package:thumbing/logic/state/chat/chat_se_state.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';

class Message extends StatelessWidget {
  final ChatSessionBloc chatSessionBloc = ChatSessionBloc();
  final ScrollController scrollController = ScrollController();
  @override
  Widget build(BuildContext context) {
    chatSessionBloc.add(ChatSessionFetched());
    return Container(
      child: Scaffold(
        appBar: AppBar(title: Text("消息"), backgroundColor: Colors.black12),
        body: BlocProvider(
          create: (context) => chatSessionBloc,
          child: BlocBuilder<ChatSessionBloc, ChatSessionState>(
            builder: (context, state) {
              if (state is ChatSessionInitial) {
                return Center(
                  child: CircularProgressIndicator(),
                );
              } else if (state is ChatSessionSuccess) {
                return ListView.builder(
                  itemCount: state.hasReachedMax
                      ? state.chatSessions.length
                      : state.chatSessions.length + 1,
                  itemBuilder: (BuildContext context, int index) {
                    return index == state.chatSessions.length
                        ? Container()
                        : ChatSessionWidget(
                            chatSession: state.chatSessions[index],
                          );
                  },
                  controller: scrollController,
                );
              } else {
                return Center(
                  child: Text('加载失败'),
                );
              }
            },
          ),
        ),
      ),
    );
  }
}

class ChatSessionWidget extends StatelessWidget {
  final ChatSession chatSession;
  const ChatSessionWidget({@required this.chatSession, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Container(
        child: ListTile(
          leading: Builder(builder: (context) {
            if (chatSession.read) {
              return Icon(
                Feather.message_circle,
                color: Colors.grey,
                size: ScreenUtils.getInstance().getHeight(30),
              );
            } else {
              return Badge(
                badgeContent: Text(
                  chatSession.noReadCount.toString(),
                  style: TextStyle(color: Colors.white),
                ),
                child: Icon(
                  Feather.message_circle,
                  color: Colors.grey,
                  size: ScreenUtils.getInstance().getHeight(30),
                ),
              );
            }
          }),
          title: Text(chatSession.user.nickName),
          subtitle: Text(
            chatSession.lastMessage,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
          ),
          trailing: Text(chatSession.howLongBrfore),
        ),
      ),
      onTap: () {
        Navigator.pushNamed(
          context,
          '/chat',
          arguments: {
            "chatSession": chatSession,
            "chatSessionBloc": BlocProvider.of<ChatSessionBloc>(context)
          },
        );
      },
    );
  }
}
