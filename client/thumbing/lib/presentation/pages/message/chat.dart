import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/chat/chat_record.dart';
import 'package:thumbing/data/model/chat/chat_session.dart';
import 'package:thumbing/logic/bloc/chat/chat_re_bloc.dart';
import 'package:thumbing/logic/bloc/chat/chat_se_bloc.dart';
import 'package:thumbing/logic/event/chat/chat_re_event.dart';
import 'package:thumbing/logic/state/chat/chat_re_state.dart';
import 'package:thumbing/presentation/widgets/send_text_widget.dart';

class Chat extends StatelessWidget {
  final ScrollController scrollController = ScrollController();
  final ChatSession session;
  final ChatSessionBloc chatSessionBloc;
  Chat({@required this.session, @required this.chatSessionBloc, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        appBar: AppBar(
            title: Text(session.user.nickName),
            backgroundColor: Colors.black12),
        body: BlocProvider(
          create: (context) => ChatRecordBloc(chatSessionBloc: chatSessionBloc)
            ..add(
              ChatRecordFetched(sessionId: session.id),
            ),
          child: BlocBuilder<ChatRecordBloc, ChatRecordState>(
            builder: (context, state) {
              if (state is ChatRecordInitial) {
                return Center(
                  child: CircularProgressIndicator(),
                );
              } else if (state is ChatRecordSuccess) {
                return Column(
                  children: <Widget>[
                    Flexible(
                      child: ListView.builder(
                        reverse: true,
                        itemCount: state.hasReachedMax
                            ? state.chatRecords.length
                            : state.chatRecords.length + 1,
                        itemBuilder: (BuildContext context, int index) {
                          return index == state.chatRecords.length
                              ? Container()
                              : MessageWidget(
                                  chatRecord: state.chatRecords[index],
                                  isNewDay: false);
                        },
                        controller: scrollController,
                      ),
                    ),
                    Divider(height: 1),
                    Container(
                      padding: EdgeInsets.only(top: 5, bottom: 5),
                      child: SendTextFieldWidget(
                        autoFocus: false,
                        margin: const EdgeInsets.only(
                            left: 15.0, right: 15.0, bottom: 5),
                        onSubmitted: (value) {
                          Navigator.pushNamed(context, '/personal/myMoment');
                        },
                        onTab: () {},
                      ),
                    ),
                  ],
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

class MessageWidget extends StatelessWidget {
  final ChatRecord chatRecord;
  final bool isNewDay;
  const MessageWidget(
      {@required this.chatRecord, @required this.isNewDay, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(bottom: 10),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: chatRecord.isSender
            ? MainAxisAlignment.end
            : MainAxisAlignment.start,
        children: <Widget>[
          !chatRecord.isSender
              ? Container(
                  child: CustomPaint(
                    painter: ChatBoxPainter(
                        height: 20, width: 15, color: Colors.grey[300]),
                  ),
                )
              : Container(),
          Container(
            child: Text(chatRecord.message),
            margin: EdgeInsets.only(left: 15, right: 15),
            padding: EdgeInsets.only(
              left: 20,
              right: 20,
              top: 20,
              bottom: 20,
            ),
            decoration: BoxDecoration(
              color: chatRecord.isSender ? Colors.grey[200] : Colors.grey[300],
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          chatRecord.isSender
              ? Transform.rotate(
                  angle: pi,
                  child: Container(
                    child: CustomPaint(
                      painter: ChatBoxPainter(
                          height: 20, width: 15, color: Colors.grey[200]),
                    ),
                  ),
                )
              : Container(),
        ],
      ),
    );
  }
}

class ChatBoxPainter extends CustomPainter {
  final double width;
  final double height;
  final Color color;
  ChatBoxPainter(
      {@required this.height, @required this.width, @required this.color});
  @override
  void paint(Canvas canvas, Size size) {
    Path path = Path()
      ..moveTo(0, height / 2)
      ..lineTo(width, height)
      ..lineTo(width, 0)
      ..lineTo(0, height / 2);
    Paint paint = Paint()
      ..style = PaintingStyle.fill
      ..color = color;
    canvas.drawPath(path, paint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;

  @override
  bool shouldRebuildSemantics(CustomPainter oldDelegate) => false;
}
