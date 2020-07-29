import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/content/comment.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';
import 'package:thumbing/logic/bloc/content/detail/d_moments_bloc.dart';
import 'package:thumbing/logic/event/content/detail/d_moments_event.dart';
import 'package:thumbing/logic/state/content/detail/d_moments_state.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';
import 'package:thumbing/presentation/widgets/send_big_widget.dart';
import 'package:thumbing/presentation/widgets/send_text_widget.dart';

class MomentsDetailPage extends StatelessWidget {
  final String id;

  const MomentsDetailPage({
    this.id,
    Key key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black26,
        title: Text("帖子"),
      ),
      body: BlocProvider(
        create: (context) => MomentsDetailBloc()
          ..add(
            MomentsDetailFetched(id),
          ),
        child: BlocBuilder<MomentsDetailBloc, MomentsDetailState>(
          builder: (context, state) {
            if (state is MomentsDetailInitial) {
              return Center(
                child: CircularProgressIndicator(),
              );
            } else if (state is MomentsDetailSuccess) {
              return Column(
                children: <Widget>[
                  Flexible(
                    child: ListView.builder(
                      itemCount: state.momentsDetail.innerComments == null
                          ? 2
                          : state.momentsDetail.innerComments.length + 2,
                      itemBuilder: (BuildContext context, int index) {
                        return index == 0
                            ? MomentsContentWidget(
                                momentsDetail: state.momentsDetail,
                              )
                            : index == 1
                                ? CommentsTitleWidget()
                                : (state.momentsDetail.innerComments == null ||
                                        state.momentsDetail.innerComments
                                                .length ==
                                            0)
                                    ? Container(
                                        padding: EdgeInsets.all(10),
                                        margin: EdgeInsets.all(20),
                                        child: Text("暂无评论"),
                                      )
                                    : MomentsCommentsWidget(
                                        comment: state.momentsDetail
                                            .innerComments[index - 2],
                                        index: index - 1,
                                      );
                      },
                    ),
                  ),
                  Divider(height: 1.0),
                  SendTextFieldWidget(
                    autoFocus: false,
                    margin: const EdgeInsets.only(
                        left: 15.0, right: 15.0, bottom: 5),
                    hintText: "请输入评论内容",
                    onSubmitted: (value) {
                      Navigator.pushNamed(context, '/personal/myMoment');
                    },
                    onTab: () {},
                  ),
                ],
              );
            } else {
              return Center(
                child: Text('加载失败'),
              );
            }
          },
        ),
      ),
    ));
  }
}

class MomentsContentWidget extends StatelessWidget {
  final MomentsDetail momentsDetail;
  const MomentsContentWidget({this.momentsDetail, Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Card(
      elevation: 3,
      child: Column(
        children: <Widget>[
          Row(
            children: <Widget>[
              Container(
                padding: EdgeInsets.only(left: 10, right: 10),
                margin: EdgeInsets.only(top: 20, left: 10, right: 10),
                child: Text(
                  momentsDetail.nickName + "：",
                  style: TextStyle(fontSize: 14.0),
                ),
              ),
            ],
          ),
          Row(
            children: <Widget>[
              Container(
                padding: EdgeInsets.only(left: 10, right: 10),
                margin: EdgeInsets.only(top: 10, left: 20, right: 20),
                child: Text(momentsDetail.howLongBefore,
                    style: TextStyle(fontSize: 12.0, color: Colors.grey)),
              ),
            ],
          ),
          Container(
            padding: EdgeInsets.only(left: 10, right: 10, bottom: 20),
            margin: EdgeInsets.only(top: 10, left: 20, right: 20),
            child:
                Text(momentsDetail.content, style: TextStyle(fontSize: 16.0)),
          ),
        ],
      ),
    ));
  }
}

class CommentsTitleWidget extends StatelessWidget {
  const CommentsTitleWidget({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(left: 20.0),
      padding: EdgeInsets.all(10),
      child: Text(
        "评论",
        style: TextStyle(fontWeight: FontWeight.w500),
      ),
    );
  }
}

class MomentsCommentsWidget extends StatelessWidget {
  final InnerComment comment;
  final int index;

  const MomentsCommentsWidget(
      {@required this.comment, @required this.index, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Card(
        elevation: 10,
        child: Column(
          children: <Widget>[
            Row(
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(top: 10),
                  margin: EdgeInsets.only(left: 20.0),
                  child: Text(
                    comment.fromName,
                    style: TextStyle(fontSize: 14.0),
                  ),
                ),
                Spacer(),
                Container(
                  margin: EdgeInsets.only(right: 20.0),
                  child: Text(
                    index.toString() + "楼",
                    style: TextStyle(fontSize: 14.0, color: Colors.grey),
                  ),
                ),
              ],
            ),
            Row(
              children: <Widget>[
                Container(
                  margin: EdgeInsets.only(left: 20.0, right: 20.0),
                  child: Text(comment.howLongBefore,
                      style: TextStyle(fontSize: 12.0, color: Colors.grey)),
                ),
              ],
            ),
            GestureDetector(
              child: Container(
                margin: EdgeInsets.only(left: 20.0, right: 20.0),
                padding: EdgeInsets.only(bottom: 20),
                child: Builder(builder: (context) {
                  if (comment.innerComments == null ||
                      comment.innerComments.length == 0) {
                    return Text(
                      comment.content,
                      style: TextStyle(fontSize: 16.0),
                    );
                  } else {
                    return Column(
                      children: <Widget>[
                        Container(
                          child: Text(
                            comment.content,
                            style: TextStyle(fontSize: 16.0),
                          ),
                        ),
                        Container(
                          margin: EdgeInsets.only(left: 30.0, right: 30.0),
                          child: GestureDetector(
                            child: Row(children: <Widget>[
                              Icon(
                                Icons.keyboard_arrow_down,
                                color: Colors.grey,
                              ),
                              Text(
                                "查看其他回复",
                                style: TextStyle(
                                    fontSize: 12.0, color: Colors.grey),
                              ),
                            ]),
                            onTap: () => {},
                          ),
                        ),
                      ],
                    );
                  }
                }),
              ),
              onTap: () {
                showModalBottomSheet(
                  context: context,
                  builder: (BuildContext context) {
                    return SendTextBigFieldWidget(
                      height: ScreenUtils.getInstance().getHeight(300),
                      autoFocus: false,
                      margin: const EdgeInsets.only(
                          left: 15.0, right: 15.0, bottom: 5),
                      hintText: "请输入评论内容",
                      onSubmitted: (value) {
                        Navigator.pushNamed(context, '/personal/myMoment');
                      },
                      onTab: () {},
                    );
                  },
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
