import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/content/moments_rep.dart';
import 'package:thumbing/logic/event/content/comments_event.dart';
import 'package:thumbing/logic/state/content/comments_state.dart';

class CommentsBloc extends Bloc<CommentsEvent, CommentsState> {
  MomentsRepository momentsRepository;

  CommentsBloc() : super(CommentsInitial()) {
    momentsRepository = MomentsRepository();
  }

  @override
  Stream<CommentsState> mapEventToState(CommentsEvent event) async* {
    final currentState = state;
    if (event is CommentsFetched) {
      try {
        if (currentState is CommentsInitial) {
          final momentsDetail =
              await momentsRepository.getMomentsDetail(event.id);
          yield CommentsSuccess(
              momentsDetail: momentsDetail, isLoading: false);
        }
      } catch (_) {
        yield CommentsFailure();
      }
    }
    if (event is CommentsRefresh) {
      try {
        if (currentState is CommentsSuccess && !currentState.isLoading) {
          yield currentState.copyWith(isLoading: true);
          final momentsDetail =
              await momentsRepository.getMomentsDetail(event.id);
          yield CommentsSuccess(
              momentsDetail: momentsDetail, isLoading: false);
        }
      } catch (_) {
        yield CommentsFailure();
      }
    }

  }
}
