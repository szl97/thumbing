import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:thumbing/logic/bloc/content/article_bloc.dart';
import 'package:thumbing/logic/bloc/content/moments_bloc.dart';
import 'package:thumbing/logic/event/content/moments_event.dart';

part '../../event/content/thumb_event.dart';
part '../../state/content/thumb_state.dart';

class ThumbBloc extends Bloc<ThumbEvent, ThumbState> {
  final MomentsBloc momentsBloc;
  final ArticleBloc articleBloc;
  ThumbBloc({this.momentsBloc, this.articleBloc}) : super(ThumbInitial());

  @override
  Stream<ThumbState> mapEventToState(ThumbEvent event) async* {
    // TODO: implement mapEventToState
    if(event is SetThumbDetails){
      if((event.id != null && event.contentType != null) || event.commentId != null){
        yield ThumbInitialFinished(id: event.id, thumbsNum: event.thumbsNum, isThumb: event.isThumb, commentId: event.commentId, contentType: event.contentType);
      }
    }
    if(event is AddThumb){
      if(event.commentId == null) {
        if (event.contentType == "moments" && momentsBloc != null) {
          momentsBloc.add(AddMomentsThumb(event.id, event.index));
        }
      }
      yield ThumbInitialFinished(id: event.id, thumbsNum: event.thumbsNum + 1, isThumb: true, commentId: event.commentId, contentType: event.contentType);
    }
    if(event is CancelThumb){
      if(event.commentId == null) {
        if (event.contentType == "moments" && momentsBloc != null) {
          momentsBloc.add(CancelMomentsThumb(event.id, event.index));
        }
      }
      yield ThumbInitialFinished(id: event.id, thumbsNum: event.thumbsNum <= 0 ? 0 : event.thumbsNum - 1, isThumb: false, commentId: event.commentId, contentType: event.contentType);
    }
  }
}
