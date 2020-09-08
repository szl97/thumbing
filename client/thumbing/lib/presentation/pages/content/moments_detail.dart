import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';
import 'package:thumbing/logic/bloc/content/comments_bloc.dart';
import 'package:thumbing/logic/bloc/content/thumb_bloc.dart';
import 'package:thumbing/logic/event/content/comments_event.dart';
import 'package:thumbing/logic/state/content/comments_state.dart';
import 'package:thumbing/presentation/util/format_time_utils.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';
import 'package:thumbing/presentation/widgets/send_text_widget.dart';

import 'comment.dart';

class MomentsDetailPage extends StatelessWidget {
  final MomentsPageResultItems moments;

  const MomentsDetailPage({
    this.moments,
    Key key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        child: Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.blueAccent,
              title: Text("帖子"),
            ),
            body: Column(
              children: <Widget>[
                Flexible(child: BlocProvider(
                    create: (context) =>
                    CommentsBloc()
                      ..add(CommentsFetched(moments.id, "moments")),
                    child: BlocBuilder<CommentsBloc, CommentsState>(
                        builder: (context, state) {
                          return CustomScrollView(
                            slivers: <Widget>[
                              SliverToBoxAdapter(
                                child: MomentsContentWidget(
                                    momentsDetail: moments),
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
                                      comment: state.momentsDetail
                                          .innerComments[index - 1],
                                      index: index,
                                      onSubmit: (value) {
                                        Navigator.pushNamed(
                                            context, '/personal/myMoment');
                                      },
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
                )),
                Divider(height: 1.0),
                Container(
                  padding: EdgeInsets.only(top: 5, bottom: 5),
                  child: SendTextFieldWidget(
                    autoFocus: false,
                    margin: const EdgeInsets.only(
                        left: 15.0, right: 15.0, bottom: 5),
                    hintText: "请输入评论内容",
                    onSubmitted: (value) {
                      Navigator.pushNamed(context, '/personal/myMoment');
                    },
                    onTab: () {},
                  ),
                ),
              ],
            )
        ));
  }
}

class MomentsContentWidget extends StatelessWidget {
  final MomentsPageResultItems momentsDetail;
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
                padding: EdgeInsets.only(left: ScreenUtils.getInstance().getWidth(10), right: ScreenUtils.getInstance().getWidth(10)),
                margin: EdgeInsets.only(top: ScreenUtils.getInstance().getHeight(20), left: ScreenUtils.getInstance().getWidth(10), right: ScreenUtils.getInstance().getWidth(10)),
                child: Text(
                  momentsDetail.title,
                  style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 18), fontWeight: FontWeight.bold),
                ),
              ),
            ],
          ),
          Row(
            children: <Widget>[
              Container(
                padding: EdgeInsets.only(left: ScreenUtils.getInstance().getWidth(10), right: ScreenUtils.getInstance().getWidth(10)),
                margin: EdgeInsets.only(top: ScreenUtils.getInstance().getHeight(10), left: ScreenUtils.getInstance().getWidth(20), right: ScreenUtils.getInstance().getWidth(20)),
                child: Text(FormatTimeUtils.toDescriptionString(momentsDetail.createTime),
                    style: TextStyle(fontSize: ScreenUtils.getScaleSp(context, 12), color: Colors.grey)),
              ),
            ],
          ),
          Container(
            padding: EdgeInsets.only(left: ScreenUtils.getInstance().getWidth(10), right: ScreenUtils.getInstance().getWidth(10), bottom: ScreenUtils.getInstance().getHeight(20)),
            margin: EdgeInsets.only(top: ScreenUtils.getInstance().getHeight(10), left: ScreenUtils.getInstance().getWidth(20), right: ScreenUtils.getInstance().getWidth(20)),
            child:
                Text(momentsDetail.content, style: TextStyle(fontSize: 16.0)),
          ),
          Container(
            margin: EdgeInsets.only(right: ScreenUtils.getInstance().getWidth(20)),

            child:
            BlocProvider(
              create: (context) => ThumbBloc()..add(SetThumbDetails(id: momentsDetail.id, thumbsNum: momentsDetail.thumbingNum, isThumb: momentsDetail.isThumb)),
              child: BlocBuilder<ThumbBloc, ThumbState>(
                builder: (context, state){
                  if(state is ThumbInitialFinished){
                    return Row(
                      children: <Widget>[
                        Spacer(),
                        IconButton(
                            icon: Icon(
                              Icons.thumb_up,
                              color: state.isThumb ? Colors.blueAccent : Colors.grey,
                            ),
                            onPressed: () => {
                              state.isThumb ? BlocProvider.of<ThumbBloc>(context).add(CancelThumb(id: state.id, thumbsNum: state.thumbsNum, isThumb: state.isThumb))
                                  : BlocProvider.of<ThumbBloc>(context).add(AddThumb(id: state.id, thumbsNum: state.thumbsNum, isThumb: state.isThumb))
                            }),
                        Text(state.thumbsNum.toString()),
                      ],
                    );
                  }
                  else{
                    return Row(
                      children: <Widget>[
                        Spacer(),
                        IconButton(
                            icon: Icon(
                              Icons.thumb_up,
                              color: momentsDetail.isThumb ? Colors.blueAccent : Colors.grey,
                            ),
                            onPressed: () => {
                              momentsDetail.isThumb ? BlocProvider.of<ThumbBloc>(context).add(CancelThumb(id: momentsDetail.id, thumbsNum: momentsDetail.thumbingNum, isThumb: momentsDetail.isThumb))
                                  : BlocProvider.of<ThumbBloc>(context).add(AddThumb(id: momentsDetail.id, thumbsNum: momentsDetail.thumbingNum, isThumb: momentsDetail.isThumb))
                            }),
                        Text(momentsDetail.thumbingNum.toString()),
                      ],
                    );
                  }
                },
              ),
            ),
          ),
        ],
      ),
    ));
  }
}
