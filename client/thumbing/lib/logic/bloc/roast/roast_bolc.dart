import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/roast/roast_rep.dart';
import 'package:thumbing/logic/event/roast/roast_event.dart';
import 'package:thumbing/logic/state/roast/roast_state.dart';

class RoastBloc extends Bloc<RoastEvent, RoastState> {
  RoastRepository roastRepository;

  RoastBloc() : super(RoastInitial()) {
    roastRepository = RoastRepository();
  }

  @override
  Stream<RoastState> mapEventToState(RoastEvent event) async* {
    final currentState = state;
    if (event is RoastFetched) {
      try {
        if (currentState is RoastInitial) {
          final roasts = await roastRepository.getRoasts();
          yield roasts.isEmpty ? RoastFailure() : RoastSuccess(
              roasts: roasts,
              hasReachedMax: false,
              currentPosition: 0,
              isLoading: false);
          return;
        }
        if (currentState is RoastSuccess) {
          final roasts = await roastRepository.getRoasts();
          yield roasts.isEmpty
              ? currentState.copyWith(hasReachedMax: true, currentPosition: 0)
              : RoastSuccess(
                  currentPosition: 0,
                  roasts: roasts,
                  hasReachedMax: false,
                  isLoading: false);
        }
      } catch (_) {
        yield RoastFailure();
      }
    }
    if (event is NextRoast) {
      if (currentState is RoastSuccess) {
        try {
          if (event.position > currentState.currentPosition) {
            yield currentState.copyWith(
              currentPosition: currentState.currentPosition + 1,
            );
            if (currentState.roasts.length - 1 ==
                currentState.currentPosition) {
              this.add(RoastFetched());
            }
          }
          if (event.position < currentState.currentPosition) {
            yield currentState.copyWith(
              currentPosition: currentState.currentPosition - 1,
            );
          }
        } catch (_) {
          yield RoastFailure();
        }
      }
    }
  }

  bool _hasReachedMax(RoastState state) =>
      state is RoastSuccess && state.hasReachedMax;
}
