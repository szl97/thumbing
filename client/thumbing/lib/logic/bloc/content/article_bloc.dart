import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:thumbing/data/repository/content/article_rep.dart';
import 'package:thumbing/logic/bloc/content/all_content_bloc.dart';
import 'package:thumbing/logic/event/content/article_event.dart';
import 'package:thumbing/logic/state/content/all_content_state.dart';
import 'package:thumbing/logic/state/content/article_state.dart';

class ArticleBloc extends Bloc<ArticleEvent, ArticleState> {
  ArticleRepository articleRepository;
  @required
  AllContentBloc allContentBloc;
  StreamSubscription allContentSubscription;

  ArticleBloc(this.allContentBloc) : super(ArticleInitial()) {
    articleRepository = ArticleRepository();
    allContentSubscription = allContentBloc.listen((state) {
      if (state is AllContentFailure) {
        this.add(ArticleInitialFailed());
      }
      if (state is AllContentSuccess) {
        this.add(ArticleInitialSuccess(articles: state.allContent.articles));
      }
    });
  }

  @override
  Stream<ArticleState> mapEventToState(ArticleEvent event) async* {
    final currentState = state;
    if (event is ArticleFetched && !_hasReachedMax(currentState)) {
      try {
        if (currentState is ArticleInitial) {
          final articles = await articleRepository.getArticles();
          yield ArticleSuccess(
              articles: articles,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is ArticleSuccess) {
          final articles = await articleRepository.getArticles();
          yield articles.isEmpty
              ? currentState.copyWith(hasReachedMax: true)
              : ArticleSuccess(
                  articles: currentState.articles + articles,
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
          final articles = await articleRepository.getArticles();
          yield ArticleSuccess(
              articles: articles,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
        if (currentState is ArticleFailure) {
          final articles = await articleRepository.getArticles();
          yield ArticleSuccess(
              articles: articles,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
      } catch (_) {
        yield ArticleFailure();
      }
    } else if (event is ArticleInitialSuccess) {
      try {
        if (currentState is ArticleInitial) {
          yield ArticleSuccess(
              articles: event.articles,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is ArticleFailure) {
          yield ArticleSuccess(
              articles: event.articles,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
      } catch (_) {
        yield ArticleFailure();
      }
    } else if (event is ArticleInitialFailed) {
      try {
        if (currentState is ArticleInitial) {
          yield ArticleFailure();
          return;
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
    allContentSubscription.cancel();
    super.close();
  }
}
