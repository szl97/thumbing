import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/roast/roast_rep.dart';
import 'package:thumbing/logic/event/roast/roast_event.dart';
import 'package:thumbing/logic/state/roast/roast_state.dart';

class MyRoastBloc extends Bloc<RoastEvent, RoastState> {
  RoastRepository roastRepository;

  MyRoastBloc() : super(RoastInitial()) {
    roastRepository = RoastRepository();
  }

  @override
  Stream<RoastState> mapEventToState(RoastEvent event) async* {
    final currentState = state;
    if (event is RoastFetched && !_hasReachedMax(currentState)) {
      try {
        if (currentState is RoastInitial) {
          final roasts = await roastRepository.getRoasts();
          yield roasts.isEmpty ? RoastFailure() : RoastSuccess(
              roasts: roasts,
              hasReachedMax: false,
              currentPosition: 0,
              currentPage: 0,
              isLoading: false);
          return;
        }
        if (currentState is RoastSuccess) {
          final roasts = await roastRepository.getRoasts();
          yield roasts.isEmpty
              ? currentState.copyWith(hasReachedMax: true)
              : RoastSuccess(
                  currentPosition: currentState.currentPosition + 1,
                  roasts: currentState.roasts + roasts,
                  hasReachedMax: false,
                  currentPage: currentState.currentPage,
                  isLoading: false);
        }
      } catch (_) {
        yield RoastFailure();
      }
    }
  }

  bool _hasReachedMax(RoastState state) =>
      state is RoastSuccess && state.hasReachedMax;
}
