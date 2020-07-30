import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/logic/bloc/user/auth_bloc.dart';
import 'package:thumbing/logic/event/user/auth_event.dart';

class Personal extends StatelessWidget {
  String name;
  Personal({Key key, this.name}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
          backgroundColor: Colors.white,
          appBar: AppBar(title: Text("个人空间"), backgroundColor: Colors.black12),
          body: SafeArea(
            child: CustomScrollView(
              physics: const BouncingScrollPhysics(),
              shrinkWrap: false,
              slivers: <Widget>[
                SliverAppBar(
                  floating: true,
                  backgroundColor: Colors.black26,
                  flexibleSpace: FlexibleSpaceBar(
                    centerTitle: true,
                    title: Text(
                      this.name,
                      style: TextStyle(
                          color: Colors.white,
                          fontStyle: FontStyle.normal,
                          fontWeight: FontWeight.w300,
                          fontSize: 15.0),
                    ),
                    background: Row(
                      children: <Widget>[
                        Spacer(),
                        CircleAvatar(
                          radius: 54.0,
                          backgroundImage: AssetImage(
                              "assets/image/personal_background.png"),
                        ),
                        Spacer(),
                      ],
                    ),
                  ),
                  expandedHeight: 200.0,
                ),
                SliverToBoxAdapter(
                  child: GestureDetector(
                    behavior: HitTestBehavior.opaque,
                    child: Row(
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.only(
                              left: 25.0, top: 15.0, bottom: 20.0, right: 10.0),
                          child: Text("我的帖子"),
                        )
                      ],
                    ),
                    onTap: () =>
                        Navigator.pushNamed(context, '/personal/myMoment'),
                  ),
                ),
                SliverToBoxAdapter(
                  child: GestureDetector(
                    behavior: HitTestBehavior.opaque,
                    child: Row(
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.only(
                              left: 25.0, top: 15.0, bottom: 20.0, right: 10.0),
                          child: Text("我的诉说"),
                        )
                      ],
                    ),
                    onTap: () =>
                        Navigator.pushNamed(context, '/personal/myRoast'),
                  ),
                ),
                SliverToBoxAdapter(
                  child: GestureDetector(
                    behavior: HitTestBehavior.opaque,
                    child: Row(
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.only(
                              left: 25.0, top: 15.0, bottom: 20.0, right: 10.0),
                          child: Text("我的文章"),
                        )
                      ],
                    ),
                    onTap: () =>
                        Navigator.pushNamed(context, '/personal/myArticle'),
                  ),
                ),
                SliverToBoxAdapter(
                  child: GestureDetector(
                    behavior: HitTestBehavior.opaque,
                    child: Row(
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.only(
                              left: 25.0, top: 15.0, bottom: 20.0, right: 10.0),
                          child: Text("退出登录"),
                        )
                      ],
                    ),
                    onTap: () {
                      BlocProvider.of<AuthBloc>(context)
                          .add(CancelAuthentication());
                      Navigator.pushNamedAndRemoveUntil(
                          context, '/', (route) => false);
                    },
                  ),
                )
              ],
            ),
          )),
    );
  }
}
