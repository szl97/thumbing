import 'package:flutter/material.dart';

class Message extends StatefulWidget {
  Message({Key key}) : super(key: key);

  @override
  MessageState createState() => MessageState();
}

class MessageState extends State<Message> {
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
      appBar: AppBar(title: Text("消息"), backgroundColor: Colors.black12),
    ));
  }
}
