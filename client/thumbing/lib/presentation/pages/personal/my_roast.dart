import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/roast/output/roast_page_result_entity.dart';
import 'package:thumbing/logic/bloc/roast/my_roast_bloc.dart';
import 'package:thumbing/logic/event/roast/roast_event.dart';
import 'package:thumbing/logic/state/roast/roast_state.dart';
import 'package:thumbing/presentation/widgets/bottom_loader.dart';

class MyRoast extends StatelessWidget {
  final MyRoastBloc myRoastBloc = MyRoastBloc();

  @override
  Widget build(BuildContext context) {
    myRoastBloc.add(RoastFetched());
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
          appBar: AppBar(title: Text("我的吐槽"), backgroundColor: Colors.black12),
          body: BlocProvider(
            create: (context)=>myRoastBloc,
            child: Container(
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
          ),
        ));
  }
}
class MyRoastWidget extends StatelessWidget {
  final RoastPageResultItem roast;
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
                          Text(roast.thumbingNum.toString() + "人拥抱过"),
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