import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/article/output/article_page_result_entity.dart';

abstract class ArticleState extends Equatable {
  const ArticleState();

  @override
  List<Object> get props => [];
}

class ArticleInitial extends ArticleState {}

class ArticleFailure extends ArticleState {}

class ArticleSuccess extends ArticleState {
  final int currentPage;
  final int position;
  final List<ArticlePageResultItem> articles;
  final bool hasReachedMax;
  final bool isLoading;

  const ArticleSuccess(
      {this.currentPage, this.articles, this.hasReachedMax, this.isLoading, this.position});

  ArticleSuccess copyWith(
      {List<ArticlePageResultItem> articles,
      bool hasReachedMax, int position,
      int currentPage,
      bool isLoading}) {
    return ArticleSuccess(
        position:position?? this.position,
        currentPage: currentPage ?? this.currentPage,
        articles: articles ?? this.articles,
        hasReachedMax: hasReachedMax ?? this.hasReachedMax,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props => [articles, hasReachedMax, currentPage, isLoading, position];
}
