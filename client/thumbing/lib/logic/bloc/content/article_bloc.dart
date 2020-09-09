import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/content/article_rep.dart';
import 'package:thumbing/logic/event/content/article_event.dart';
import 'package:thumbing/logic/state/content/article_state.dart';

class ArticleBloc extends Bloc<ArticleEvent, ArticleState> {
  ArticleRepository articleRepository;
  ArticleBloc() : super(ArticleInitial()) {
    articleRepository = ArticleRepository();
  }

  @override
  Stream<ArticleState> mapEventToState(ArticleEvent event) async* {
    final currentState = state;
    if (event is ArticleFetched && !_hasReachedMax(currentState)) {
      try {
        if (currentState is ArticleInitial) {
          final articles = await articleRepository.getArticles(0, 2147483647);
          yield articles == null || articles.items.isEmpty ? ArticleFailure() : ArticleSuccess(
              articles: articles.items,
              position: articles.position,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is ArticleSuccess) {
          final articles = await articleRepository.getArticles(currentState.currentPage, currentState.position);
          yield articles == null || articles.items.isEmpty
              ? currentState.copyWith(hasReachedMax: true)
              : ArticleSuccess(
                  articles: currentState.articles + articles.items,
                  position: articles.position,
                  hasReachedMax: false,
                  currentPage: currentState.currentPage + 1,
                  isLoading: false);
        }
      } catch (_) {
        yield ArticleFailure();
      }
    } else if (event is ArticleRefresh) {
      try {
        if (currentState is ArticleSuccess && !currentState.isLoading) {
          yield currentState.copyWith(isLoading: true);
          final articles = await articleRepository.getArticles(0, 2147483647);
          yield ArticleSuccess(
              articles: articles.items,
              position: articles.position,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
        if (currentState is ArticleFailure) {
          final articles = await articleRepository.getArticles(0, 2147483647);
          yield ArticleSuccess(
              articles: articles.items,
              position: articles.position,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
      } catch (_) {
        yield ArticleFailure();
      }
    }
  }

  bool _hasReachedMax(ArticleState state) =>
      state is ArticleSuccess && state.hasReachedMax;

  @override
  Future<void> close() {
    super.close();
  }
}
