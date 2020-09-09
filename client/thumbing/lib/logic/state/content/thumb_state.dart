part of '../../bloc/content/thumb_bloc.dart';

abstract class ThumbState extends Equatable {
  const ThumbState();
  @override
  List<Object> get props => [];
}

class ThumbInitial extends ThumbState {}

class ThumbInitialFinished extends ThumbState{
  const ThumbInitialFinished({this.id, this.thumbsNum, this.isThumb, this.commentId, this.contentType});
  final String id;
  final int thumbsNum;
  final bool isThumb;
  final String contentType;
  final String commentId;
  @override
  // TODO: implement props
  List<Object> get props => [id, thumbsNum, isThumb, commentId, contentType];
}
