part of '../../bloc/content/thumb_bloc.dart';

abstract class ThumbEvent extends Equatable {
  const ThumbEvent({this.id, this.thumbsNum, this.isThumb, this.contentType, this.commentId, this.index});
  final String id;
  final String contentType;
  final String commentId;
  final int thumbsNum;
  final bool isThumb;
  final int index;
  @override
  // TODO: implement props
  List<Object> get props => [id, thumbsNum, isThumb, contentType, commentId, index];
}

class SetThumbDetails extends ThumbEvent{
  SetThumbDetails({id, thumbsNum, isThumb, contentType, commentId, index}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, contentType:contentType, commentId:commentId, index:index);
}

class AddThumb extends ThumbEvent{
  AddThumb({id, thumbsNum, isThumb, contentType, commentId, index}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, contentType:contentType, commentId:commentId, index:index);
}

class CancelThumb extends ThumbEvent{
  CancelThumb({id, thumbsNum, isThumb, contentType, commentId, index}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, contentType:contentType, commentId:commentId, index:index);
}