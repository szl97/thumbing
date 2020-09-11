import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:thumbing/logic/bloc/content/comments_bloc.dart';
import 'package:thumbing/logic/event/content/comments_event.dart';

part '../../event/content/send_comment_event.dart';
part '../../state/content/send_comment_state.dart';

class SendCommentBloc extends Bloc<SendCommentEvent, SendCommentState> {
  final CommentsBloc commentsBloc;
  SendCommentBloc({this.commentsBloc}) : super(SendCommentInitial());

  @override
  Stream<SendCommentState> mapEventToState(SendCommentEvent event) async* {
    // TODO: implement mapEventToState
    final currentState = state;
    if(event is InitializeSentState){
      if(currentState is SendCommentInitial){
        yield TextFieldFinished(
          contentType: event.contentType,
          contentId: event.contentId,
          commentId: event.commentId,
          nickName: event.nickName,
        );
        return;
      }
    }
    if(event is ChangeTarget){
      if(currentState is TextFieldFinished){
        yield currentState.copyWithoutTarget(commentId: event.commentId, nickName: event.nickName);
        return;
      }
    }
    if(event is ChangeText){
      if(currentState is TextFieldFinished){
        yield currentState.copyWith(text: event.text);
        return;
      }
    }
    if(event is SubmitComment){
      if(currentState is TextFieldFinished) {
        commentsBloc.add(CommentsRefresh(event.contentId, event.contentType));
        yield TextFieldFinished(
          contentType: event.contentType,
          contentId: event.contentId,
          commentId: event.commentId,
          nickName: currentState.nickName,
        );
        return;
      }
    }
  }
}
