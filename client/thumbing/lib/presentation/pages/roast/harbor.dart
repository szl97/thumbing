import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/roast/roast.dart';
import 'package:thumbing/logic/bloc/roast/my_roast_bloc.dart';
import 'package:thumbing/logic/bloc/roast/roast_bolc.dart';
import 'package:thumbing/logic/event/roast/roast_event.dart';
import 'package:thumbing/logic/state/roast/roast_state.dart';
import 'package:thumbing/presentation/widgets/bottom_loader.dart';

class Harbor extends StatefulWidget {
  Harbor({Key key}) : super(key: key);

  @override
  _HarborState createState() => _HarborState();
}

class _HarborState extends State<Harbor> {
  RoastBloc roastBloc;
  MyRoastBloc myRoastBloc;
  PageController pageController;
  ScrollController scrollController;

  @override
  void initState() {
    super.initState();
    roastBloc = RoastBloc();
    myRoastBloc = MyRoastBloc();
    roastBloc.add(RoastFetched());
    myRoastBloc.add(RoastFetched());
    pageController = PageController();
    pageController.addListener(() {
      nextPage();
    });
    scrollController = ScrollController();
    scrollController.addListener(() {
      if (scrollController.position.pixels >=
          scrollController.position.maxScrollExtent) {
        // 滑动到了底部
        // 这里可以执行上拉加载逻辑
        loadMoreMyRoast();
      }
    });
  }

  Future<Null> nextPage() async {
    if (roastBloc.state is RoastSuccess) {
      roastBloc.add(NextRoast(position: pageController.page.floor()));
    }
  }

  Future<Null> loadMoreMyRoast() async {
    if (myRoastBloc.state is RoastSuccess) {
      myRoastBloc.add(RoastFetched());
    }
  }

  @override
  Widget build(BuildContext context) {
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
                height: 350.0,
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
                height: 30,
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
                height: 230.0,
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
                margin: EdgeInsets.only(top: 50),
                height: 130.0,
                width: 200.0,
                child: Text(
                  roast.content,
                  style: TextStyle(height: 1.8),
                  maxLines: 3,
                  overflow: TextOverflow.ellipsis,
                ),
              ),
              onTap: () => {},
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 20.0, bottom: 20.0),
            width: 200.0,
            height: 20.0,
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
          GestureDetector(
            child: Container(
              margin: EdgeInsets.only(top: 20.0, bottom: 20.0),
              width: 200.0,
              height: 20.0,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Spacer(),
                  Text(
                    "抱一抱",
                    style: TextStyle(color: Colors.blueAccent),
                  ),
                ],
              ),
            ),
            onTap: () => {},
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
