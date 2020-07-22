import 'package:flutter/material.dart';

import 'routes/routes.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "thumbing",
      debugShowCheckedModeBanner: false, //去掉debug图标
      initialRoute: '/', //初始化的时候加载的路由
      onGenerateRoute: onGenerateRoute,
    );
  }
}
