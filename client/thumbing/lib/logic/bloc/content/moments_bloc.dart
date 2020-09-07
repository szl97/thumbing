import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/content/moments_rep.dart';
import 'package:thumbing/logic/event/content/moments_event.dart';
import 'package:thumbing/logic/state/content/moments_state.dart';

class MomentsBloc extends Bloc<MomentsEvent, MomentState> {
  MomentsRepository momentsRepository;


  MomentsBloc() : super(MomentsInitial()) {
    momentsRepository = MomentsRepository();
  }

  @override
  Stream<MomentState> mapEventToState(MomentsEvent event) async* {
    final currentState = state;
    if (event is MomentsFetched && !_hasReachedMax(currentState)) {
      try {
        if (currentState is MomentsInitial) {
          final moments = await momentsRepository.getMoments(0, 2147483647);
          yield moments == null || moments.items.isEmpty ? MomentsFailure() :
          MomentSuccess(
              moments: moments.items,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is MomentSuccess) {
          final moments = await momentsRepository.getMoments(currentState.currentPage, currentState.position);
          yield moments == null || moments.items.isEmpty
              ? currentState.copyWith(hasReachedMax: true)
              : MomentSuccess(
                  moments: currentState.moments + moments.items,
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
          final moments = await momentsRepository.getMoments(0, 2147483647);
          yield moments == null || moments.items.isEmpty ? MomentsFailure() : MomentSuccess(
              moments: moments.items,
              hasReachedMax: false,
              currentPage: 0,
              isLoading: false);
        }
        if (currentState is MomentsFailure) {
          final moments = await momentsRepository.getMoments(0, 2147483647);
          yield moments == null || moments.items.isEmpty ? MomentsFailure() : MomentSuccess(
              moments: moments.items,
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
    super.close();
  }
}
