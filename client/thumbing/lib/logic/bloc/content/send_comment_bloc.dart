import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

part '../../event/content/send_comment_event.dart';
part '../../state/content/send_comment_state.dart';

class SendCommentBloc extends Bloc<SendCommentEvent, SendCommentState> {
  SendCommentBloc() : super(SendCommentInitial());

  @override
  Stream<SendCommentState> mapEventToState(SendCommentEvent event) async* {
    // TODO: implement mapEventToState
    final currentState = state;
    if(event is InitializeSentState){
      if(currentState is SendCommentInitial){
        yield TextFieldFinished(
          contentType: event.contentType,
          commentId: event.contentId,
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
      print(event.text);
      if(currentState is TextFieldFinished){
        yield currentState.copyWith(text: event.text);
        return;
      }
    }
    if(event is SubmitComment){
      print("object");
      if(state is TextFieldFinished) {
        yield TextFieldFinished(
          contentType: "event.contentType",
          commentId: "event.contentId",
        );
        return;
      }
    }
  }
}
