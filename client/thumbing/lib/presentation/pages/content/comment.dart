import 'package:flutter/material.dart';
import 'package:thumbing/data/model/content/comment.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';
import 'package:thumbing/presentation/widgets/send_big_widget.dart';

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
                    return Column(
                      children: <Widget>[
                        Text(
                          comment.content,
                          style: TextStyle(fontSize: 16.0),
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
            backgroundColor: Colors.black26,
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
        elevation: 20,
        child: Column(
          children: <Widget>[
            Row(
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(top: 10, left: 20, right: 20),
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
      child: Card(
        elevation: 0,
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
          ],
        ),
      ),
    );
  }
}
