import 'package:equatable/equatable.dart';

abstract class ArticleEvent extends Equatable {
  const ArticleEvent();
  @override
  List<Object> get props => [];
}

class ArticleFetched extends ArticleEvent {}

class ArticleRefresh extends ArticleEvent {}

