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
    }else if(event is AddMomentsThumb){
      if(currentState is MomentSuccess){
        var state = currentState.copyWith();
        if(state.moments.length < event.index){
          var moments = state.moments[event.index];
          if(moments.id == event.id){
            if(!moments.isThumb){
              moments.isThumb = true;
              moments.thumbingNum+=1;
            }
            yield state;
            return;
          }
        }
        state.moments.forEach((element) {
          if(element.id == event.id){
            if(!element.isThumb){
              element.isThumb = true;
              element.thumbingNum+=1;
            }
          }
        });
        yield state;
      }
    }else if(event is CancelMomentsThumb){
      if(currentState is MomentSuccess){
        var state = currentState.copyWith();
        if(state.moments.length < event.index){
          var moments = state.moments[event.index];
          if(moments.id == event.id){
            if(moments.isThumb){
              moments.isThumb = false;
              moments.thumbingNum-=1;
            }
            yield state;
            return;
          }
        }
        state.moments.forEach((element) {
          if(element.id == event.id){
            if(element.isThumb){
              element.isThumb = false;
              element.thumbingNum-=1;
            }
          }
        });
        yield state;
        yield state;
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
