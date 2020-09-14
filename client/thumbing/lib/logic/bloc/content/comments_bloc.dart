import 'package:bloc/bloc.dart';
import 'package:thumbing/data/model/content/comment.dart';
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
    }else if (event is CommentsRefresh) {
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
    }else if(event is AddCommentThumb){
      if(currentState is CommentsSuccess){
        var state = currentState.copyWith();
        List<InnerComment> list = currentState.momentsDetail.innerComments.where((element) => element.commentId == event.parentId);
        if(list.length > event.index){
          var comment = list[event.index];
          if(comment.commentId == event.commentId){
            if(!comment.isThumb){
              comment.isThumb = true;
              comment.thumbings+=1;
            }
            yield state;
            return;
          }
        }
        list.forEach((element) {
          if(element.commentId == event.commentId){
            if(!element.isThumb){
              element.isThumb = true;
              element.thumbings+=1;
            }
          }
        });
        yield state;
      }
    }else if(event is CancelCommentThumb){
      if(currentState is CommentsSuccess){
        var state = currentState.copyWith();
        List<InnerComment> list = currentState.momentsDetail.innerComments.where((element) => element.commentId == event.parentId);
        if(list.length > event.index){
          var comment = list[event.index];
          if(comment.commentId == event.commentId){
            if(comment.isThumb){
              comment.isThumb = false;
              comment.thumbings-=1;
            }
            yield state;
            return;
          }
        }
        list.forEach((element) {
          if(element.commentId == event.commentId){
            if(element.isThumb){
              element.isThumb = false;
              element.thumbings-=1;
            }
          }
        });
        yield state;
      }
    }
  }
}
