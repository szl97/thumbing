part of '../../bloc/content/thumb_bloc.dart';

abstract class ThumbState extends Equatable {
  const ThumbState({this.id, this.thumbsNum, this.isThumb, this.commentId, this.contentType});
  final String id;
  final int thumbsNum;
  final bool isThumb;
  final String contentType;
  final String commentId;
  @override
  // TODO: implement props
  List<Object> get props => [id, thumbsNum, isThumb, commentId, contentType];
}

class ThumbInitial extends ThumbState {
  const ThumbInitial() : super(isThumb:false);
}

class ThumbInitialFinished extends ThumbState{
  const ThumbInitialFinished({id, thumbsNum, isThumb, commentId, contentType}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, commentId: commentId, contentType: contentType);
}
