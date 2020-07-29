import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/content/moments_rep.dart';
import 'package:thumbing/logic/event/content/detail/d_moments_event.dart';
import 'package:thumbing/logic/state/content/detail/d_moments_state.dart';

class MomentsDetailBloc extends Bloc<MomentsDetailEvent, MomentsDetailState> {
  MomentsRepository momentsRepository;

  MomentsDetailBloc() : super(MomentsDetailInitial()) {
    momentsRepository = MomentsRepository();
  }

  @override
  Stream<MomentsDetailState> mapEventToState(MomentsDetailEvent event) async* {
    final currentState = state;
    if (event is MomentsDetailFetched) {
      try {
        if (currentState is MomentsDetailInitial) {
          final momentsDetail =
              await momentsRepository.getMomentsDetail(event.id);
          yield MomentsDetailSuccess(
              momentsDetail: momentsDetail, isLoading: false);
        }
      } catch (_) {
        yield MomentsDetailFailure();
      }
    }
    if (event is MomentsDetailRefresh) {
      try {
        if (currentState is MomentsDetailSuccess && !currentState.isLoading) {
          yield currentState.copyWith(isLoading: true);
          final momentsDetail =
              await momentsRepository.getMomentsDetail(event.id);
          yield MomentsDetailSuccess(
              momentsDetail: momentsDetail, isLoading: false);
        }
      } catch (_) {
        yield MomentsDetailFailure();
      }
    }
  }
}
