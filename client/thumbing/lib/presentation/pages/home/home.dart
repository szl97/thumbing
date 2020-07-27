import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/logic/bloc/content/all_content_bloc.dart';
import 'package:thumbing/logic/bloc/content/moments_bloc.dart';
import 'package:thumbing/logic/event/content/moments_event.dart';
import 'package:thumbing/logic/state/content/moments_state.dart';
import 'home_app_bar.dart' as home_bar;
import 'package:thumbing/presentation/widgets/search_text_fird_widget.dart';
import 'package:thumbing/data/model/content/moments.dart';
import 'package:thumbing/logic/bloc/content/article_bloc.dart';
import 'package:thumbing/logic/event/content/article_event.dart';
import 'package:thumbing/logic/state/content/article_state.dart';
import 'package:thumbing/data/model/content/article.dart';

class Home extends StatefulWidget {
  Home({Key key}) : super(key: key);
  @override
  _HomeState createState() => _HomeState();
}

var _tabs = ['动态', '诉说', "文章"];

class _HomeState extends State<Home> {
  MomentsBloc momentsBloc;
  ArticleBloc articleBloc;
  ScrollController _mScrollController;
  ScrollController _aScrollController;
  @override
  void initState() {
    super.initState();
    AllContentBloc allContentBloc = BlocProvider.of<AllContentBloc>(context);
    momentsBloc = MomentsBloc(allContentBloc);
    _mScrollController = ScrollController();
    // 监听ListView是否滚动到底部
    _mScrollController.addListener(() {
      if (_mScrollController.position.pixels >=
          _mScrollController.position.maxScrollExtent) {
        // 滑动到了底部
        // 这里可以执行上拉加载逻辑
        _loadMoreMoments();
      }
    });
    articleBloc = ArticleBloc(allContentBloc);
    _aScrollController = ScrollController();
    // 监听ListView是否滚动到底部
    _aScrollController.addListener(() {
      if (_aScrollController.position.pixels >=
          _aScrollController.position.maxScrollExtent) {
        // 滑动到了底部
        // 这里可以执行上拉加载逻辑
        _loadMoreArticles();
      }
    });
  }

  @override
  void dispose() {
    super.dispose();
    // 这里不要忘了将监听移除
    _mScrollController.dispose();
    _aScrollController.dispose();
  }

  Future<Null> _loadMoreMoments() async {
    momentsBloc.add(MomentsFetched());
  }

  Future<Null> _handleRefreshMoments() async {
    momentsBloc.add(MomentsRefresh());
  }

  Future<Null> _loadMoreArticles() async {
    articleBloc.add(ArticleFetched());
  }

  Future<Null> _handleRefreshArticles() async {
    articleBloc.add(ArticleRefresh());
  }

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
                        onSubmitted: (value) {
                          Navigator.pushNamed(context, '/personal/myMoment');
                        },
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
                          .toList()),
                ),
              ),
            ];
          },
          body: MultiBlocProvider(
              providers: [
                BlocProvider(create: (context) => momentsBloc),
                BlocProvider(create: (context) => articleBloc),
              ],
              child: TabBarView(children: <Widget>[
                Center(
                  child: Container(
                      padding: EdgeInsets.only(top: 30),
                      margin: EdgeInsets.only(top: 10),
                      child: RefreshIndicator(
                          child: getMonmets(),
                          onRefresh: _handleRefreshMoments)),
                ),
                Center(
                  child: Container(
                      padding: EdgeInsets.only(top: 30),
                      margin: EdgeInsets.only(top: 10),
                      child: RefreshIndicator(
                          child: getMonmets(),
                          onRefresh: _handleRefreshMoments)),
                ),
                Center(
                  child: Container(
                      padding: EdgeInsets.only(top: 30),
                      margin: EdgeInsets.only(top: 10),
                      child: RefreshIndicator(
                          child: getArticles(),
                          onRefresh: _handleRefreshArticles)),
                ),
              ]))),
    );
  }

  getMonmets() {
    return BlocBuilder<MomentsBloc, MomentState>(
      bloc: momentsBloc,
      builder: (context, state) {
        if (state is MomentsInitial) {
          return Center(
            child: CircularProgressIndicator(),
          );
        } else if (state is MomentSuccess) {
          return ListView.builder(
            shrinkWrap: true,
            itemCount: state.hasReachedMax
                ? state.moments.length
                : state.moments.length + 1,
            itemBuilder: (BuildContext context, int index) {
              return index >= state.moments.length
                  ? BottomLoader()
                  : MomentsWidget(moments: state.moments[index]);
            },
            controller: _mScrollController,
          );
        } else {
          return Center(
            child: Text('failed to fetch moments'),
          );
        }
      },
    );
  }

  getArticles() {
    return BlocBuilder<ArticleBloc, ArticleState>(
      bloc: articleBloc,
      builder: (context, state) {
        if (state is ArticleInitial) {
          return Center(
            child: CircularProgressIndicator(),
          );
        } else if (state is ArticleSuccess) {
          return ListView.builder(
            shrinkWrap: true,
            itemCount: state.hasReachedMax
                ? state.articles.length
                : state.articles.length + 1,
            itemBuilder: (BuildContext context, int index) {
              return index >= state.articles.length
                  ? BottomLoader()
                  : ArticleWidget(article: state.articles[index]);
            },
            controller: _aScrollController,
          );
        } else {
          return Center(
            child: Text('failed to fetch moments'),
          );
        }
      },
    );
  }
}

class BottomLoader extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      child: Center(
        child: SizedBox(
          width: 33,
          height: 33,
          child: CircularProgressIndicator(
            strokeWidth: 1.5,
          ),
        ),
      ),
    );
  }
}

class MomentsWidget extends StatelessWidget {
  final Moments moments;
  const MomentsWidget({@required this.moments, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 1,
      child: Container(
          padding: EdgeInsets.all(10),
          margin: EdgeInsets.only(bottom: 10),
          child: GestureDetector(
            child: Column(
              children: <Widget>[
                ListTile(
                  title: Text(
                    "来自 " + moments.nickName + ":",
                    style: TextStyle(fontSize: 14),
                  ),
                  subtitle: Column(
                    children: <Widget>[
                      Text(
                        moments.abstracts,
                        style: TextStyle(height: 1.8),
                        maxLines: 3,
                        overflow: TextOverflow.ellipsis,
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Text(moments.thumbings.toString() +
                              "点赞" +
                              moments.comments.toString() +
                              "评论"),
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

class ArticleWidget extends StatelessWidget {
  final Article article;
  const ArticleWidget({this.article, Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 1,
      child: Container(
          padding: EdgeInsets.all(10),
          margin: EdgeInsets.only(bottom: 10),
          child: GestureDetector(
            child: Column(
              children: <Widget>[
                ListTile(
                  title: Text(article.title),
                  subtitle: Column(
                    children: <Widget>[
                      SizedBox(height: 5.0),
                      Row(children: <Widget>[
                        Text(
                          article.nickName,
                          style: TextStyle(fontSize: 14),
                        )
                      ]),
                      SizedBox(height: 5.0),
                      Text(
                        article.abstracts,
                        style: TextStyle(height: 1.8),
                        maxLines: 3,
                        overflow: TextOverflow.ellipsis,
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Text(article.thumbings.toString() +
                              "点赞" +
                              article.comments.toString() +
                              "评论"),
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