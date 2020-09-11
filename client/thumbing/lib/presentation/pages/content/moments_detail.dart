
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';
import 'package:thumbing/logic/bloc/content/comments_bloc.dart';
import 'package:thumbing/logic/bloc/content/moments_bloc.dart';
import 'package:thumbing/logic/bloc/content/send_comment_bloc.dart';
import 'package:thumbing/logic/bloc/content/thumb_bloc.dart';
import 'package:thumbing/logic/event/content/comments_event.dart';
import 'package:thumbing/logic/state/content/comments_state.dart';
import 'package:thumbing/presentation/util/format_time_utils.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';
import 'package:thumbing/presentation/widgets/send_text_widget.dart';

import 'comment.dart';

class MomentsDetailPage extends StatelessWidget {
  final MomentsPageResultItems moments;
  final MomentsBloc momentsBloc;
  final int index;
  const MomentsDetailPage({
    this.moments,
    this.index,
    this.momentsBloc,
    Key key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final _focusNode = FocusNode();
    final TextEditingController _controller = new TextEditingController();
    return Container(
        child: Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.blueAccent,
              title: Text("帖子"),
            ),
            body: MultiBlocProvider(
              providers: [
                BlocProvider<CommentsBloc>(
                  create: (context) =>CommentsBloc()..add(CommentsFetched(moments.id, "moments")),
                ),
                BlocProvider<SendCommentBloc>(
                  create: (context) => SendCommentBloc(commentsBloc: BlocProvider.of<CommentsBloc>(context))..add(InitializeSentState(contentId: moments.id, contentType: "moments")),
                ),
              ],
              child: BlocBuilder<SendCommentBloc, SendCommentState>(
                builder: (context, state){
                  return Column(
                    children: <Widget>[
                      Flexible(child: BlocBuilder<CommentsBloc, CommentsState>(
                              builder: (context, state) {
                                return CustomScrollView(
                                  slivers: <Widget>[
                                    SliverToBoxAdapter(
                                      child: MomentsContentWidget(
                                        index: index,
                                        momentsBloc: momentsBloc,
                                        momentsDetail: moments,
                                        focusNode: _focusNode,),
                                    ),
                                    SliverLayoutBuilder(builder: (context,
                                        constraints) {
                                      if (state is CommentsInitial) {
                                        return SliverToBoxAdapter(
                                          child: Center(child: CircularProgressIndicator(),),
                                        );
                                      } else if (state is CommentsSuccess) {
                                        return
                                          SliverList(delegate: SliverChildBuilderDelegate(
                                                  (BuildContext context, int index){
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
                                                  focusNode: _focusNode,
                                                  comment: state.momentsDetail
                                                        .innerComments[index - 1],
                                                  index: index,
                                                );
                                              }, childCount: state.momentsDetail
                                              .innerComments == null
                                              ? 1
                                              : state.momentsDetail.innerComments
                                              .length + 1));
                                      } else {
                                        return SliverToBoxAdapter(child: Center(
                                          child: Text('加载失败'),
                                        ),) ;
                                      }
                                    })
                                  ],
                                );
                              })
                      ),
                      Divider(height: 1.0),
                      state is TextFieldFinished ? Container(
                        padding: EdgeInsets.only(top: 5, bottom: 5),
                        child: SendTextFieldWidget(
                          controller: _controller,
                          focusNode: _focusNode,
                          autoFocus: false,
                          margin: const EdgeInsets.only(left: 15.0, bottom: 5),
                          hintText: "请输入评论内容",
                          prefixText: state.nickName == null ? null : "回复 " + state.nickName+" ",
                          onChanged: (string)=>BlocProvider.of<SendCommentBloc>(context).add(ChangeText(text: string)),
                          onSubmitted: () {
                            if(state.text != null) {
                              BlocProvider.of<SendCommentBloc>(context).add(SubmitComment(
                                  contentId: state.contentId,
                                  contentType: state.contentType,
                                  commentId: state.commentId,
                                  text: state.text));
                              _controller.clear();
                            }
                          } ,
                          onTab: () {},
                        ),
                      ) : Container(),
                    ],
                  );
                },
              ),
            )
        ));
  }
}

class MomentsContentWidget extends StatelessWidget {
  final MomentsPageResultItems momentsDetail;
  final int index;
  final MomentsBloc momentsBloc;
  final FocusNode focusNode;

  const MomentsContentWidget(
      {this.momentsDetail, this.momentsBloc, this.index, this.focusNode, Key key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Card(
          elevation: 1,
          child: Column(
            children: <Widget>[
              Row(
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.only(
                        left: ScreenUtils.getInstance().getWidth(10),
                        right: ScreenUtils.getInstance().getWidth(10)),
                    margin: EdgeInsets.only(
                        top: ScreenUtils.getInstance().getHeight(20),
                        left: ScreenUtils.getInstance().getWidth(10),
                        right: ScreenUtils.getInstance().getWidth(10)),
                    child: Text(
                      momentsDetail.title,
                      style: TextStyle(
                          fontSize: ScreenUtils.getScaleSp(context, 18),
                          fontWeight: FontWeight.bold),
                    ),
                  ),
                ],
              ),
              Row(
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.only(
                        left: ScreenUtils.getInstance().getWidth(10),
                        right: ScreenUtils.getInstance().getWidth(10)),
                    margin: EdgeInsets.only(
                        top: ScreenUtils.getInstance().getHeight(10),
                        left: ScreenUtils.getInstance().getWidth(20),
                        right: ScreenUtils.getInstance().getWidth(20)),
                    child: Text(FormatTimeUtils.toDescriptionString(
                        momentsDetail.createTime),
                        style: TextStyle(
                            fontSize: ScreenUtils.getScaleSp(context, 12),
                            color: Colors.grey)),
                  ),
                ],
              ),
              Container(
                padding: EdgeInsets.only(
                    left: ScreenUtils.getInstance().getWidth(10),
                    right: ScreenUtils.getInstance().getWidth(10),
                    bottom: ScreenUtils.getInstance().getHeight(20)),
                margin: EdgeInsets.only(
                    top: ScreenUtils.getInstance().getHeight(10),
                    left: ScreenUtils.getInstance().getWidth(20),
                    right: ScreenUtils.getInstance().getWidth(20)),
                child: GestureDetector(
                  child: Text(
                      momentsDetail.content, style: TextStyle(fontSize: 16.0)),
                  onTap: () {
                    BlocProvider.of<SendCommentBloc>(context).add(
                        ChangeTarget());
                    FocusScope.of(context).requestFocus(focusNode);
                    focusNode.requestFocus();
                  },),
              ),
              Container(
                margin: EdgeInsets.only(
                    right: ScreenUtils.getInstance().getWidth(20)),
                child: BlocProvider(
                  create: (context) =>
                  ThumbBloc(momentsBloc: momentsBloc)
                    ..add(SetThumbDetails(id: momentsDetail.id,
                        thumbsNum: momentsDetail.thumbingNum,
                        isThumb: momentsDetail.isThumb,
                        contentType: "moments",
                        index: index)),
                  child: BlocBuilder<ThumbBloc, ThumbState>(
                    builder: (context, state) {
                      return Row(
                        children: <Widget>[
                          Spacer(),
                          IconButton(
                              icon: Icon(
                                Icons.thumb_up,
                                color: state.isThumb
                                    ? Colors.blueAccent
                                    : Colors
                                    .grey,
                              ),
                              onPressed: () {
                                state.isThumb ? BlocProvider.of<ThumbBloc>(
                                    context)
                                    .add(CancelThumb(
                                    id: state.id,
                                    thumbsNum: state.thumbsNum,
                                    isThumb: state.isThumb,
                                    contentType: "moments",
                                    index: index))
                                    : BlocProvider.of<ThumbBloc>(context)
                                    .add(AddThumb(
                                    id: state.id,
                                    thumbsNum: state.thumbsNum,
                                    isThumb: state.isThumb,
                                    contentType: "moments",
                                    index: index
                                ));
                              }),
                          Text(state.thumbsNum.toString()),
                        ],
                      );
                    },
                  ),
                ),
              ),
            ],
          ),
        ));
  }
}
