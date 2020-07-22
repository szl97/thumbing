import 'package:flutter/material.dart';

class MyMoment extends StatefulWidget {
  MyMoment({Key key}) : super(key: key);

  @override
  _MyMomentState createState() => _MyMomentState();
}

class _MyMomentState extends State<MyMoment> {
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
      appBar: AppBar(title: Text("我的动态"), backgroundColor: Colors.black12),
    ));
  }
}
