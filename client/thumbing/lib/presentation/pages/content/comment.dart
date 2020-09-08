
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/content/comment.dart';
import 'package:thumbing/logic/bloc/content/comments_bloc.dart';
import 'package:thumbing/logic/event/content/comments_event.dart';
import 'package:thumbing/logic/state/content/comments_state.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';
import 'package:thumbing/presentation/widgets/send_big_widget.dart';


class CommentsListWidget extends StatelessWidget{
  const CommentsListWidget({this.contentId, this.contentType, Key key}) : super(key: key);
  final String contentType;
  final String contentId;
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return BlocProvider(
      create: (context) => CommentsBloc()..add(CommentsFetched(contentId, contentType)),
      child: BlocBuilder<CommentsBloc, CommentsState>(
        builder: (context, state) {
          if (state is CommentsInitial) {
            return Center(
              child: CircularProgressIndicator(),
            );
          } else if (state is CommentsSuccess) {
            return
              ListView.builder(
                itemCount: state.momentsDetail.innerComments == null
                    ? 1
                    : state.momentsDetail.innerComments.length + 1,
                itemBuilder: (BuildContext context, int index) {
                  return index == 0
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
                      : CommentsWidget(
                    comment: state.momentsDetail
                        .innerComments[index - 1],
                    index: index - 1,
                    onSubmit: (value) {
                      Navigator.pushNamed(
                          context, '/personal/myMoment');
                    },
                  );
                },
              );
          } else {
            return Center(
              child: Text('加载失败'),
            );
          }
        },
      ),
    );
  }

}
class CommentsTitleWidget extends StatelessWidget {
  const CommentsTitleWidget({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Divider(height: 1.0,),
        Container(
          padding: EdgeInsets.only(top: 10, bottom: 10),
          child: Center(
          child: Text(
            "评论",
            style: TextStyle(fontWeight: FontWeight.w500),
          ),
        ),),
        Divider(height: 1.0),
      ],
    );
  }
}

class CommentsWidget extends StatelessWidget {
  final InnerComment comment;
  final int index;
  final ValueChanged<String> onSubmit;

  const CommentsWidget(
      {@required this.comment,
      @required this.index,
      @required this.onSubmit,
      Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: <Widget>[
          Row(
            children: <Widget>[
              Container(
                padding: EdgeInsets.only(top: ScreenUtils.getInstance().getHeight(10)),
                margin: EdgeInsets.only(left: ScreenUtils.getInstance().getWidth(20)),
                child: Text(
                  comment.fromName,
                  style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 14)),
                ),
              ),
              Spacer(),
              Container(
                margin: EdgeInsets.only(right: ScreenUtils.getInstance().getWidth(20)),
                child: Text(
                  index.toString() + "楼",
                  style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 14), color: Colors.grey),
                ),
              ),
            ],
          ),
          Row(
            children: <Widget>[
              Container(
                margin: EdgeInsets.only(left: ScreenUtils.getInstance().getWidth(20)),
                child: Text(comment.howLongBefore,
                    style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 12), color: Colors.grey)),
              ),
            ],
          ),
          GestureDetector(
            child: Container(
              padding: EdgeInsets.only(bottom: ScreenUtils.getInstance().getHeight(20),
                  top: ScreenUtils.getInstance().getHeight(10),
                  left: ScreenUtils.getInstance().getWidth(20),
                  right: ScreenUtils.getInstance().getWidth(20)),
              child: Builder(builder: (context) {
                if (comment.innerComments == null ||
                    comment.innerComments.length == 0) {
                  return Column(
                    children: <Widget>[
                      Text(
                        comment.content,
                        style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 16)),
                      ),
                      Row(
                        children: <Widget>[
                          Spacer(),
                          IconButton(
                              icon: Icon(
                                Icons.thumb_up,
                                color: Colors.grey,
                              ),
                              onPressed: () => {}),
                          Text(comment.thumbings.toString()),
                        ],
                      ),
                    ],
                  );
                } else {
                  return Column(
                    children: <Widget>[
                      Container(
                        child: Text(
                          comment.content,
                          style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 16)),
                        ),
                      ),
                      Container(
                        padding: EdgeInsets.only(left: ScreenUtils.getInstance().getWidth(20)),
                        child: GestureDetector(
                          child: Row(children: <Widget>[
                            Spacer(),
                            Icon(
                              Icons.keyboard_arrow_down,
                              color: Colors.blueAccent[100],
                            ),
                            Text(
                              "查看其他回复",
                              style:
                                  TextStyle(fontSize: ScreenUtils.getScaleSp(context, 12), color: Colors.blueAccent[100]),
                            ),
                            Spacer(),
                            IconButton(
                                icon: Icon(
                                  Icons.thumb_up,
                                  color: Colors.grey,
                                ),
                                onPressed: () => {}),
                            Text(comment.thumbings.toString()),
                          ]),
                          onTap: () => {
                            Navigator.pushNamed(
                              context,
                              '/content/childComment',
                              arguments: {
                                "index": index,
                                "comment": comment,
                                "comments": comment.innerComments,
                                "onSubmit": onSubmit,
                              },
                            )
                          },
                        ),
                      ),
                      Divider(height: 1.0),
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
                    autoFocus: true,
                    margin: const EdgeInsets.only(
                        left: 15.0, right: 15.0, bottom: 5),
                    hintText: "请输入评论内容",
                    onSubmitted: onSubmit,
                    onTab: () {},
                  );
                },
              );
            },
          ),
        ],
      ),
    );
  }
}

class ChildCommentListWidget extends StatelessWidget {
  final int index;
  final InnerComment comment;
  final List<InnerComment> comments;
  final ValueChanged<String> onSubmit;
  const ChildCommentListWidget(
      {@required this.index,
      @required this.comment,
      @required this.comments,
      @required this.onSubmit,
      Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: ScreenUtils.getInstance().getHeight(400),
      child: Container(
        child: Scaffold(
          appBar: AppBar(
            backgroundColor: Colors.blueAccent,
            title: Text("第" + index.toString() + "楼"),
          ),
          body: ListView.builder(
            itemCount: comments.length + 1,
            itemBuilder: (context, index) {
              if (index == 0) {
                return ParentCommentWidget(
                  comment: comment,
                  onSubmit: onSubmit,
                );
              } else {
                return ChildCommentWidget(
                  comment: comments[index - 1],
                  onSubmit: onSubmit,
                );
              }
            },
          ),
        ),
      ),
    );
  }
}

class ParentCommentWidget extends StatelessWidget {
  final InnerComment comment;
  final ValueChanged<String> onSubmit;
  const ParentCommentWidget(
      {@required this.comment, @required this.onSubmit, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: 10),
      child: Card(
        elevation: 8,
        child: Column(
          children: <Widget>[
            Row(
              children: <Widget>[
                Container(
                  padding:
                      EdgeInsets.only(top: 10, left: 20, right: 20, bottom: 10),
                  child: Text(
                    comment.fromName,
                    style: TextStyle(fontSize: 14),
                  ),
                ),
              ],
            ),
            Row(
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(left: 20, right: 20),
                  child: Text(
                    comment.howLongBefore,
                    style: TextStyle(fontSize: 12, color: Colors.grey),
                  ),
                ),
              ],
            ),
            GestureDetector(
              child: Container(
                padding:
                    EdgeInsets.only(left: 20, right: 20, top: 10, bottom: 10),
                child: Text(
                  comment.content,
                  style: TextStyle(fontSize: 16),
                ),
              ),
              onTap: () => {
                showModalBottomSheet(
                  context: context,
                  builder: (BuildContext context) {
                    return SendTextBigFieldWidget(
                      height: ScreenUtils.getInstance().getHeight(300),
                      autoFocus: true,
                      margin: const EdgeInsets.only(
                          left: 15.0, right: 15.0, bottom: 5),
                      hintText: "请输入评论内容",
                      onSubmitted: onSubmit,
                      onTab: () {},
                    );
                  },
                )
              },
            ),
            Container(
              margin: EdgeInsets.only(right: 20),
              child: Row(
                children: <Widget>[
                  Spacer(),
                  IconButton(
                      icon: Icon(
                        Icons.thumb_up,
                        color: Colors.grey,
                      ),
                      onPressed: () => {}),
                  Text(
                    comment.thumbings.toString(),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class ChildCommentWidget extends StatelessWidget {
  final InnerComment comment;
  final ValueChanged<String> onSubmit;
  const ChildCommentWidget(
      {@required this.comment, @required this.onSubmit, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: <Widget>[
          Row(
            children: <Widget>[
              Container(
                padding: EdgeInsets.only(top: 10, left: 20, right: 20),
                child: Text(
                  comment.fromName + " 回复 " + comment.toName,
                  style: TextStyle(fontSize: 14),
                ),
              ),
            ],
          ),
          Row(
            children: <Widget>[
              Container(
                padding: EdgeInsets.only(left: 20, right: 20),
                child: Text(
                  comment.howLongBefore,
                  style: TextStyle(fontSize: 12, color: Colors.grey),
                ),
              ),
            ],
          ),
          GestureDetector(
            child: Container(
              padding:
                  EdgeInsets.only(left: 20, right: 20, top: 10, bottom: 10),
              child: Text(
                comment.content,
                style: TextStyle(fontSize: 16),
              ),
            ),
            onTap: () => {
              showModalBottomSheet(
                context: context,
                builder: (BuildContext context) {
                  return SendTextBigFieldWidget(
                    height: ScreenUtils.getInstance().getHeight(300),
                    autoFocus: true,
                    margin: const EdgeInsets.only(
                        left: 15.0, right: 15.0, bottom: 5),
                    hintText: "请输入评论内容",
                    onSubmitted: onSubmit,
                    onTab: () {},
                  );
                },
              )
            },
          ),
          Container(
            margin: EdgeInsets.only(right: 20),
            child: Row(
              children: <Widget>[
                Spacer(),
                IconButton(
                    icon: Icon(
                      Icons.thumb_up,
                      color: Colors.grey,
                    ),
                    onPressed: () => {}),
                Text(
                  comment.thumbings.toString(),
                ),
              ],
            ),
          ),
          Divider(height: 1.0),
        ],
      ),
    );
  }
}
