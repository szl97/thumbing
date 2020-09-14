part of '../../bloc/content/thumb_bloc.dart';

abstract class ThumbEvent extends Equatable {
  const ThumbEvent({this.id, this.thumbsNum, this.isThumb, this.contentType, this.commentId, this.index, this.parentId});
  final String id;
  final String contentType;
  final String commentId;
  final int thumbsNum;
  final bool isThumb;
  final int index;
  final String parentId;
  @override
  // TODO: implement props
  List<Object> get props => [id, thumbsNum, isThumb, contentType, commentId, index, parentId];
}

class SetThumbDetails extends ThumbEvent{
  SetThumbDetails({id, thumbsNum, isThumb, contentType, commentId}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, contentType:contentType, commentId:commentId);
}

class AddThumb extends ThumbEvent{
  AddThumb({id, thumbsNum, isThumb, contentType, commentId, index, parentId}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, contentType:contentType, commentId:commentId, index:index, parentId:parentId);
}

class CancelThumb extends ThumbEvent{
  CancelThumb({id, thumbsNum, isThumb, contentType, commentId, index, parentId}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb, contentType:contentType, commentId:commentId, index:index, parentId:parentId);
}