import 'package:flutter/material.dart';
import 'package:thumbing/presentation/pages/content/push_content.dart';
import 'package:thumbing/presentation/pages/initial/initial.dart';
import 'package:thumbing/presentation/pages/login/login.dart';
import 'package:thumbing/presentation/pages/personal/my_article.dart';
import 'package:thumbing/presentation/pages/personal/my_moment.dart';
import 'package:thumbing/presentation/pages/personal/my_roast.dart';
import 'package:thumbing/presentation/pages/home/guid.dart';

//配置路由
final routes = {
  '/': (context) => Initial(),
  '/home': (context) => Guid(),
  '/login': (context) => LoginPage(),
  '/personal/myArticle': (context) => MyArticle(),
  '/personal/myMoment': (context) => MyMoment(),
  '/personal/myRoast': (context) => MyRoast(),
  '/content/pushContent': (context) => PushContent()
};

var onGenerateRoute = (RouteSettings settings) {
  // 统一处理
  final String name = settings.name;
  final Function pageContentBuilder = routes[name];
  if (pageContentBuilder != null) {
    if (settings.arguments != null) {
      final Route route = MaterialPageRoute(
          builder: (context) =>
              pageContentBuilder(context, arguments: settings.arguments));
      return route;
    } else {
      final Route route =
          MaterialPageRoute(builder: (context) => pageContentBuilder(context));
      return route;
    }
  }
};
