import 'package:flutter/material.dart';

class MyArticle extends StatefulWidget {
  MyArticle({Key key}) : super(key: key);

  @override
  _MyArticleState createState() => _MyArticleState();
}

class _MyArticleState extends State<MyArticle> {
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
      appBar: AppBar(title: Text("我的文章"), backgroundColor: Colors.black12),
    ));
  }
}
