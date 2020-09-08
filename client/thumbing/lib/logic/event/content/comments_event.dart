import 'package:equatable/equatable.dart';

abstract class CommentsEvent extends Equatable {
  final String id;
  final String contentType;
  const CommentsEvent(this.id, this.contentType);
  @override
  List<Object> get props => [];
}

class CommentsFetched extends CommentsEvent {
  CommentsFetched(String id, String contentType) : super(id, contentType);
}

class CommentsRefresh extends CommentsEvent {
  CommentsRefresh(String id, String contentType) : super(id, contentType);
}
