import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/article.dart';

abstract class ArticleEvent extends Equatable {
  const ArticleEvent();
  @override
  List<Object> get props => [];
}

class ArticleFetched extends ArticleEvent {}

class ArticleRefresh extends ArticleEvent {}

class ArticleInitialSuccess extends ArticleEvent {
  final List<Article> articles;
  const ArticleInitialSuccess({this.articles}) : super();
  @override
  List<Object> get props => [articles];
}

class ArticleInitialFailed extends ArticleEvent {}
