import 'package:equatable/equatable.dart';

abstract class CommentsEvent extends Equatable {
  final String id;
  final String contentType;
  const CommentsEvent(this.id, this.contentType);
  @override
  List<Object> get props => [id, contentType];
}

class CommentsFetched extends CommentsEvent {
  CommentsFetched(String id, String contentType) : super(id, contentType);
}

class CommentsRefresh extends CommentsEvent {
  CommentsRefresh(String id, String contentType) : super(id, contentType);
}

class AddCommentThumb extends CommentsEvent {
  AddCommentThumb(String id, String contentType, this.commentId, this.index, this.parentId) : super(id, contentType);
  final String commentId;
  final String parentId;
  final int index;
  @override
  List<Object> get props => [id, contentType, commentId, index, parentId];
}

class CancelCommentThumb extends CommentsEvent {
  CancelCommentThumb(String id, String contentType, this.commentId, this.index, this.parentId) : super(id, contentType);
  final String commentId;
  final String parentId;
  final int index;
  @override
  List<Object> get props => [id, contentType, commentId, index, parentId];
}
