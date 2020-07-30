import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/roast/roast.dart';
import 'package:thumbing/logic/bloc/roast/my_roast_bloc.dart';
import 'package:thumbing/logic/bloc/roast/roast_bolc.dart';
import 'package:thumbing/logic/event/roast/roast_event.dart';
import 'package:thumbing/logic/state/roast/roast_state.dart';
import 'package:thumbing/presentation/widgets/bottom_loader.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';

class Harbor extends StatelessWidget {
  final RoastBloc roastBloc = RoastBloc();
  final MyRoastBloc myRoastBloc = MyRoastBloc();
  final PageController pageController = PageController();

  @override
  Widget build(BuildContext context) {
    roastBloc.add(RoastFetched());
    myRoastBloc.add(RoastFetched());

    pageController.addListener(() {
      if (roastBloc.state is RoastSuccess) {
        roastBloc.add(NextRoast(position: pageController.page.floor()));
      }
    });

    final ScrollController scrollController = ScrollController();
    scrollController.addListener(() {
      if (scrollController.position.pixels >=
          scrollController.position.maxScrollExtent) {
        // 滑动到了底部
        // 这里可以执行上拉加载逻辑
        if (myRoastBloc.state is RoastSuccess) {
          myRoastBloc.add(RoastFetched());
        }
      }
    });
    return Container(
        child: Scaffold(
      appBar: AppBar(
        title: Text("港湾"),
        backgroundColor: Colors.black12,
      ),
      body: MultiBlocProvider(
          providers: [
            BlocProvider(create: (context) => roastBloc),
            BlocProvider(create: (context) => myRoastBloc)
          ],
          child: Column(
            children: <Widget>[
              Container(
                height: ScreenUtils.getInstance().getHeight(270),
                child: BlocBuilder<RoastBloc, RoastState>(
                  bloc: roastBloc,
                  builder: (context, state) {
                    if (state is RoastInitial) {
                      return Center(
                        child: CircularProgressIndicator(),
                      );
                    } else if (state is RoastSuccess) {
                      return PageView.builder(
                        itemCount: state.hasReachedMax
                            ? state.roasts.length
                            : state.roasts.length + 1,
                        controller: pageController,
                        itemBuilder: (BuildContext context, int index) {
                          return index >= state.roasts.length
                              ? BottomLoader()
                              : RoastWidget(roast: state.roasts[index]);
                        },
                      );
                    } else {
                      return Center(
                        child: Text('加载失败'),
                      );
                    }
                  },
                ),
              ),
              Container(
                height: ScreenUtils.getInstance().getHeight(24),
                margin: EdgeInsets.only(top: 10),
                child: BlocBuilder<MyRoastBloc, RoastState>(
                    builder: (context, state) {
                  if (state is RoastInitial) {
                    return Center(
                      child: Text(""),
                    );
                  } else if (state is RoastSuccess) {
                    return Center(
                      child: Text(
                        "我的心情轨迹",
                        style: TextStyle(
                            fontSize: 20.0, fontWeight: FontWeight.bold),
                      ),
                    );
                  } else {
                    return Center(
                      child: Text(''),
                    );
                  }
                }),
              ),
              Container(
                height: ScreenUtils.getInstance().getHeight(224),
                child: BlocBuilder<MyRoastBloc, RoastState>(
                  bloc: myRoastBloc,
                  builder: (context, state) {
                    if (state is RoastInitial) {
                      return Center(
                        child: Text(""),
                      );
                    } else if (state is RoastSuccess) {
                      return ListView.builder(
                        controller: scrollController,
                        itemCount: state.hasReachedMax
                            ? state.roasts.length
                            : state.roasts.length + 1,
                        itemBuilder: (BuildContext context, int index) {
                          return index >= state.roasts.length
                              ? BottomLoader()
                              : MyRoastWidget(roast: state.roasts[index]);
                        },
                      );
                    } else {
                      return Center(
                        child: Text('加载失败'),
                      );
                    }
                  },
                ),
              ),
            ],
          )),
    ));
  }
}

class RoastWidget extends StatelessWidget {
  final Roast roast;
  const RoastWidget({@required this.roast, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      semanticContainer: true,
      margin: EdgeInsets.all(10),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(20.0)),
      ),
      clipBehavior: Clip.antiAlias,
      elevation: 8,
      child: Column(
        children: <Widget>[
          Container(
            child: GestureDetector(
              child: Container(
                padding: EdgeInsets.all(10),
                margin: EdgeInsets.only(
                    top: ScreenUtils.getInstance().getHeight(40)),
                height: ScreenUtils.getInstance().getHeight(120),
                width: ScreenUtils.getInstance().getWidth(260),
                child: Text(
                  roast.content,
                  style: TextStyle(height: 1.8),
                  maxLines: 3,
                  overflow: TextOverflow.ellipsis,
                ),
              ),
              onTap: () => {Navigator.pushNamed(context, '/personal/myMoment')},
            ),
          ),
          Container(
            margin: EdgeInsets.only(
              top: ScreenUtils.getInstance().getHeight(16),
            ),
            width: ScreenUtils.getInstance().getWidth(200),
            height: ScreenUtils.getInstance().getHeight(15),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Spacer(),
                Text(
                  roast.thumbings.toString() + "人拥抱过",
                  style: TextStyle(color: Colors.grey),
                ),
              ],
            ),
          ),
          Spacer(),
          Container(
            margin: EdgeInsets.only(
              top: ScreenUtils.getInstance().getHeight(16),
              bottom: ScreenUtils.getInstance().getHeight(15),
            ),
            width: ScreenUtils.getInstance().getWidth(260),
            height: ScreenUtils.getInstance().getHeight(15),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Spacer(),
                GestureDetector(
                  child: Text(
                    "抱一抱",
                    style: TextStyle(color: Colors.blueAccent),
                  ),
                  onTap: () =>
                      {Navigator.pushNamed(context, '/personal/myMoment')},
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}

class MyRoastWidget extends StatelessWidget {
  final Roast roast;
  const MyRoastWidget({@required this.roast, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    print(roast.id);
    return Card(
      semanticContainer: true,
      margin: EdgeInsets.all(10),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(20.0)),
      ),
      clipBehavior: Clip.antiAlias,
      elevation: 20,
      child: Container(
          padding: EdgeInsets.all(10),
          margin: EdgeInsets.only(bottom: 10),
          child: GestureDetector(
            child: Column(
              children: <Widget>[
                ListTile(
                  title: Text(
                    "三天前:",
                    style: TextStyle(fontSize: 14),
                  ),
                  subtitle: Column(
                    children: <Widget>[
                      Text(
                        roast.content,
                        style: TextStyle(height: 1.8),
                        maxLines: 3,
                        overflow: TextOverflow.ellipsis,
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Text(roast.thumbings.toString() + "人拥抱过"),
                          IconButton(
                            icon: Icon(Icons.more_horiz),
                            onPressed: () {},
                          )
                        ],
                      )
                    ],
                  ),
                )
              ],
            ),
            onTap: () => Navigator.pushNamed(context, '/personal/myMoment'),
          )),
    );
  }
}
