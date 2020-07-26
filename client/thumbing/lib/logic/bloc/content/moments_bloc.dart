import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:thumbing/data/repository/content/moments_rep.dart';
import 'package:thumbing/logic/bloc/content/all_content_bloc.dart';
import 'package:thumbing/logic/event/content/moments_event.dart';
import 'package:thumbing/logic/state/content/all_content_state.dart';
import 'package:thumbing/logic/state/content/moments_state.dart';

class MomentsBloc extends Bloc<MomentsEvent, MomentState> {
  MomentsRepository momentsRepository;
  @required
  AllContentBloc allContentBloc;
  StreamSubscription allContentSubscription;

  MomentsBloc(this.allContentBloc) : super(MomentsInitial()) {
    momentsRepository = MomentsRepository();
    allContentSubscription = allContentBloc.listen((state) {
      if (state is AllContentFailure) {
        this.add(MomentsInitialFailed());
      }
      if (state is AllContentSuccess) {
        this.add(MomentsInitialSuccess(moments: state.allContent.moments));
      }
    });
  }

  @override
  Stream<MomentState> mapEventToState(MomentsEvent event) async* {
    final currentState = state;
    if (event is MomentsFetched && !_hasReachedMax(currentState)) {
      try {
        if (currentState is MomentsInitial) {
          final moments = await momentsRepository.getMoments();
          yield MomentSuccess(
              moments: moments,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is MomentSuccess) {
          final moments = await momentsRepository.getMoments();
          yield moments.isEmpty
              ? currentState.copyWith(hasReachedMax: true)
              : MomentSuccess(
                  moments: currentState.moments + moments,
                  hasReachedMax: false,
                  currentPage: currentState.currentPage + 1,
                  isLoading: false);
        }
      } catch (_) {
        yield MomentsFailure();
      }
    } else if (event is MomentsRefresh) {
      try {
        if (currentState is MomentSuccess && !currentState.isLoading) {
          yield currentState.copyWith(isLoading: true);
          final moments = await momentsRepository.getMoments();
          yield MomentSuccess(
              moments: moments,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
        if (currentState is MomentsFailure) {
          final moments = await momentsRepository.getMoments();
          yield MomentSuccess(
              moments: moments,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
      } catch (_) {
        yield MomentsFailure();
      }
    } else if (event is MomentsInitialSuccess) {
      try {
        if (currentState is MomentsInitial) {
          yield MomentSuccess(
              moments: event.moments,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is MomentsFailure) {
          yield MomentSuccess(
              moments: event.moments,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
      } catch (_) {
        yield MomentsFailure();
      }
    } else if (event is MomentsInitialFailed) {
      try {
        if (currentState is MomentsInitial) {
          yield MomentsFailure();
          return;
        }
      } catch (_) {
        yield MomentsFailure();
      }
    }
  }

  bool _hasReachedMax(MomentState state) =>
      state is MomentSuccess && state.hasReachedMax;

  @override
  Future<void> close() {
    allContentSubscription.cancel();
    super.close();
  }
}
