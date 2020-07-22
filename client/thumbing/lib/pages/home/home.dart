import 'package:flutter/material.dart';
import 'home_app_bar.dart' as home_bar;
import 'package:thumbing/widgets/search_text_fird_widget.dart';

class Home extends StatefulWidget {
  Home({Key key}) : super(key: key);
  @override
  _HomeState createState() => _HomeState();
}

var _tabs = ['动态', '诉说', "文章"];

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      initialIndex: 0,
      length: _tabs.length,
      child: NestedScrollView(
          headerSliverBuilder: (context, innerBoxIsScrolled) {
            return <Widget>[
              SliverOverlapAbsorber(
                handle:
                    NestedScrollView.sliverOverlapAbsorberHandleFor(context),
                sliver: home_bar.SliverAppBar(
                  pinned: true,
                  expandedHeight: 120.0,
                  primary: true,
                  titleSpacing: 0.0,
                  backgroundColor: Colors.white,
                  flexibleSpace: FlexibleSpaceBar(
                    collapseMode: CollapseMode.pin,
                    background: Container(
                      color: Colors.black26,
                      child: SearchTextFieldWidget(
                        hintText: '输入搜索内容',
                        margin: const EdgeInsets.only(left: 15.0, right: 15.0),
                        onTab: () {},
                      ),
                      alignment: Alignment(0.0, 0.0),
                    ),
                  ),
                  // The "forceElevated" property causes the SliverAppBar to show
                  // a shadow. The "innerBoxIsScrolled" parameter is true when the
                  // inner scroll view is scrolled beyond its "zero" point, i.e.
                  // when it appears to be scrolled below the SliverAppBar.
                  // Without this, there are cases where the shadow would appear
                  // or not appear inappropriately, because the SliverAppBar is
                  // not actually aware of the precise position of the inner
                  // scroll views.
                  bottomTextString: _tabs,
                  bottom: TabBar(
                    // These are the widgets to put in each tab in the tab bar.
                    tabs: _tabs
                        .map((String name) => Container(
                              child: Text(
                                name,
                              ),
                              padding: const EdgeInsets.only(bottom: 5.0),
                            ))
                        .toList(),
                  ),
                ),
              ),
            ];
          },
          body: TabBarView(children: <Widget>[
            ListView(
              children: <Widget>[
                ListTile(title: Text("这是一条动态")),
                ListTile(title: Text("这是一条动态")),
                ListTile(title: Text("这是一条动态"))
              ],
            ),
            ListView(
              children: <Widget>[
                ListTile(title: Text("这是一条诉说")),
                ListTile(title: Text("这是一条诉说")),
                ListTile(title: Text("这是一条诉说"))
              ],
            ),
            ListView(
              children: <Widget>[
                ListTile(title: Text("这是一篇文章")),
                ListTile(title: Text("这是一篇文章")),
                ListTile(title: Text("这是一篇文章"))
              ],
            ),
          ])),
    );
  }
}
