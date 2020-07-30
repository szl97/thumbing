import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/chat/chat_se_rep.dart';
import 'package:thumbing/logic/event/chat/chat_se_event.dart';
import 'package:thumbing/logic/state/chat/chat_se_state.dart';

class ChatSessionBloc extends Bloc<ChatSessionEvent, ChatSessionState> {
  ChatSessionRepository chatSessionRepository;
  ChatSessionBloc() : super(ChatSessionInitial()) {
    chatSessionRepository = ChatSessionRepository();
  }
  @override
  Stream<ChatSessionState> mapEventToState(ChatSessionEvent event) async* {
    var currentState = state;
    if (event is ChatSessionFetched && !_hasReachedMax(state)) {
      try {
        if (currentState is ChatSessionInitial) {
          var chatSessions = await chatSessionRepository.getChatSessions();
          yield ChatSessionSuccess(
            chatSessions: chatSessions,
            hasReachedMax: false,
            currentPage: 0,
            isLoading: false,
          );
          return;
        }
        if (currentState is ChatSessionSuccess && !currentState.isLoading) {
          yield currentState.copyWith(isLoading: true);
          var chatSessions = await chatSessionRepository.getChatSessions();
          if (chatSessions == null || chatSessions.length > 0) {
            yield currentState.copyWith(hasReachedMax: true);
          } else {
            yield currentState.copyWith(
              currentPage: currentState.currentPage + 1,
              chatSessions: currentState.chatSessions + chatSessions,
            );
          }
        }
      } catch (_) {
        yield ChatSessionFailure();
      }
    }
    if (event is ChatSessionAdd) {
      if (currentState is ChatSessionSuccess) {
        currentState.chatSessions.insert(0, event.chatSession);
        yield currentState;
      }
    }
  }

  bool _hasReachedMax(ChatSessionState state) =>
      state is ChatSessionSuccess && state.hasReachedMax;
}
