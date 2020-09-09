part of '../../bloc/content/send_comment_bloc.dart';

abstract class SendCommentState extends Equatable {
  const SendCommentState();
  @override
  List<Object> get props => [];
}

class SendCommentInitial extends SendCommentState {}

class TextFieldFinished extends SendCommentState{
  const TextFieldFinished({this.contentId, this.contentType, this.commentId, this.nickName, this.text}) : super();
  final String contentId;
  final String contentType;
  final String commentId;
  final String nickName;
  final String text;
  TextFieldFinished copyWith({
        String contentId,
        String contentType,
        String commentId,
        String nickName,
        String text}
      ){
    return TextFieldFinished(
      contentId: contentId??this.contentId,
      contentType: contentType??this.contentType,
      commentId : contentId??this.commentId,
      nickName: nickName??this.nickName,
      text: text??this.text,
    );
  }

  TextFieldFinished copyWithoutTarget({
    String contentId,
    String contentType,
    String commentId,
    String nickName,
    String text}
      ){
    return TextFieldFinished(
      contentId: contentId??this.contentId,
      contentType: contentType??this.contentType,
      commentId : commentId,
      nickName: nickName,
      text: text??this.text,
    );
  }
  @override
  List<Object> get props => [contentId, contentType, commentId, nickName, text];
}