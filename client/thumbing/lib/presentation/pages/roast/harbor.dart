import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/roast/output/roast_page_result_entity.dart';
import 'package:thumbing/logic/bloc/roast/roast_bolc.dart';
import 'package:thumbing/logic/event/roast/roast_event.dart';
import 'package:thumbing/logic/state/roast/roast_state.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';
import 'package:thumbing/presentation/widgets/bottom_loader.dart';

class Harbor extends StatefulWidget  {
  Harbor({Key key}) : super(key: key);
  @override
  State<StatefulWidget> createState() => _HarborState();
}

class _HarborState extends State<Harbor> with AutomaticKeepAliveClientMixin{
  RoastBloc roastBloc;
  PageController pageController;
  @override
  void initState() {
    super.initState();
    roastBloc = RoastBloc();
    pageController = PageController();
    roastBloc.add(RoastFetched());
    pageController.addListener(() {
      var state = roastBloc.state;
      if (state is RoastSuccess) {
        roastBloc.add(NextRoast(position: pageController.page.floor()));
        if (pageController.page == state.roasts.length){
          pageController.animateToPage(0, duration: Duration(milliseconds: 200), curve:Curves.ease);
        }
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
  }
  @override
  // TODO: implement wantKeepAlive
  bool get wantKeepAlive => true;

  @override
  Widget build(BuildContext context) {

    return Container(
        child: Scaffold(
          appBar: AppBar(
            title: Text("港湾"),
            backgroundColor: Colors.blueAccent,
          ),
          body: BlocProvider(
              create:(context) => roastBloc,
              child: Column(
                children: <Widget>[
                  Container(
                    height: ScreenUtils.getInstance().getHeight(400),
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
                ],
              )),
        ));
  }

}

class RoastWidget extends StatelessWidget {
  final RoastPageResultItem roast;
  const RoastWidget({@required this.roast, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      semanticContainer: true,
      margin: EdgeInsets.all(ScreenUtils.getInstance().getHeight(10)),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(20.0)),
      ),
      clipBehavior: Clip.antiAlias,
      child: Column(
        children: <Widget>[
          Container(
            child: GestureDetector(
              child: Container(
                padding: EdgeInsets.all(ScreenUtils.getInstance().getHeight(10)),
                margin: EdgeInsets.only(
                    top: ScreenUtils.getInstance().getHeight(40)),
                height: ScreenUtils.getInstance().getHeight(120),
                width: ScreenUtils.getInstance().getWidth(260),
                child: Text(
                  roast.content,
                  style: TextStyle(height: ScreenUtils.getInstance().getHeight(1.5)),
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
                  roast.thumbingNum.toString() + "人拥抱过",
                  style: TextStyle(color: Colors.grey, fontSize: ScreenUtils.getScaleSp(context,14)),
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
                    style: TextStyle(color: Colors.blueAccent, fontSize: ScreenUtils.getScaleSp(context,14)),
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

