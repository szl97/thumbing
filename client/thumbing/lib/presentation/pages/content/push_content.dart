import 'package:flutter/material.dart';

class PushContent extends StatelessWidget {
  const PushContent({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
      appBar: AppBar(title: Text("发布内容"), backgroundColor: Colors.blueAccent),
    ));
  }
}
