import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:thumbing/presentation/pages/personal/personal.dart';
import 'package:thumbing/presentation/pages/home/home.dart';
import 'package:thumbing/presentation/pages/message/message.dart';
import 'package:thumbing/presentation/pages/roast/harbor.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';

class Guid extends StatefulWidget {
  final index;

  Guid({Key key, this.index = 0}) : super(key: key);

  @override
  _GuidState createState() => _GuidState(this.index);
}

class _GuidState extends State<Guid> {
  int currentIndex;
  _GuidState(index) {
    this.currentIndex = index;
  }
  List pageList = [
    Home(),
    Harbor(),
    Message(),
    Personal(name: "Stan Sai"),
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: Container(
        height: ScreenUtils.getInstance().getHeight(70),
        width: ScreenUtils.getInstance().getWidth(70),
        padding: EdgeInsets.all(ScreenUtils.getInstance().getHeight(8)),
        margin: EdgeInsets.only(top: ScreenUtils.getInstance().getHeight(10)),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(40),
          color: Colors.white,
        ),
        child: FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () {
            Navigator.pushNamed(context, '/content/pushContent');
          },
          backgroundColor: Colors.black12,
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      body: this.pageList[this.currentIndex],
      bottomNavigationBar: BottomNavigationBar(
          currentIndex: this.currentIndex, //配置对应的索引值选中
          onTap: (int index) {
            setState(() {
              //改变状态
              this.currentIndex = index;
            });
          },
          iconSize: 20.0, //icon的大小
          fixedColor: Colors.red, //选中的颜色
          type: BottomNavigationBarType.fixed, //配置底部tabs可以有多个按钮
          items: [
            BottomNavigationBarItem(icon: Icon(Icons.home), title: Text("首页")),
            BottomNavigationBarItem(
                icon: Icon(MaterialCommunityIcons.sailing), title: Text("港湾")),
            BottomNavigationBarItem(
                icon: Icon(Icons.message), title: Text("消息")),
            BottomNavigationBarItem(
                icon: Icon(Icons.person), title: Text("我的")),
          ]),
    );
  }
}
