import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/article.dart';

abstract class ArticleState extends Equatable {
  const ArticleState();

  @override
  List<Object> get props => [];
}

class ArticleInitial extends ArticleState {}

class ArticleFailure extends ArticleState {}

class ArticleSuccess extends ArticleState {
  final int currentPage;
  final List<Article> articles;
  final bool hasReachedMax;
  final bool isLoading;

  const ArticleSuccess(
      {this.currentPage, this.articles, this.hasReachedMax, this.isLoading});

  ArticleSuccess copyWith(
      {List<Article> articles,
      bool hasReachedMax,
      int currentPage,
      bool isLoading}) {
    return ArticleSuccess(
        currentPage: currentPage ?? this.currentPage,
        articles: articles ?? this.articles,
        hasReachedMax: hasReachedMax ?? this.hasReachedMax,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props => [articles, hasReachedMax, currentPage, isLoading];
}
