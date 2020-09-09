part of '../../bloc/content/send_comment_bloc.dart';

abstract class SendCommentEvent extends Equatable {
  const SendCommentEvent();
  @override
  List<Object> get props => [];
}

class InitializeSentState extends SendCommentEvent {
  const InitializeSentState({this.contentId, this.contentType}) : super();
  final String contentId;
  final String contentType;
  @override
  List<Object> get props => [contentId, contentType];
}

class ChangeTarget extends SendCommentEvent {
  const ChangeTarget({this.commentId, this.nickName}) : super();
  final String commentId;
  final String nickName;
  @override
  List<Object> get props => [commentId, nickName];
}

class ChangeText extends SendCommentEvent {
  const ChangeText({this.text}) : super();
  final String text;
  @override
  List<Object> get props => [text];
}

class SubmitComment extends SendCommentEvent {
  const SubmitComment({this.contentId, this.contentType, this.commentId, this.text}) : super();
  final String contentId;
  final String contentType;
  final String commentId;
  final String text;
  @override
  List<Object> get props => [contentId, contentType, commentId, text];
}