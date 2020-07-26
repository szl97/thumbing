import 'package:flutter/material.dart';

class MyRoast extends StatefulWidget {
  MyRoast({Key key}) : super(key: key);

  @override
  _MyRoastState createState() => _MyRoastState();
}

class _MyRoastState extends State<MyRoast> {
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
      appBar: AppBar(title: Text("我的诉说"), backgroundColor: Colors.black12),
    ));
  }
}
