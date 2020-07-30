import 'package:bloc/bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:thumbing/data/repository/chat/chat_re_rep.dart';
import 'package:thumbing/logic/bloc/chat/chat_se_bloc.dart';
import 'package:thumbing/logic/event/chat/chat_re_event.dart';
import 'package:thumbing/logic/state/chat/chat_re_state.dart';

class ChatRecordBloc extends Bloc<ChatRecordEvent, ChatRecordState> {
  ChatRecordRepository chatRecordRepository;
  ChatSessionBloc chatSessionBloc;
  ChatRecordBloc({@required this.chatSessionBloc})
      : super(ChatRecordInitial()) {
    chatRecordRepository = ChatRecordRepository();
  }
  @override
  Stream<ChatRecordState> mapEventToState(ChatRecordEvent event) async* {
    var currentState = state;
    if (event is ChatRecordFetched && !_hasReachedMax(state)) {
      try {
        if (currentState is ChatRecordInitial) {
          var chatRecords =
              await chatRecordRepository.getChatRecords(event.sessionId);
          yield ChatRecordSuccess(
            chatRecords: chatRecords,
            hasReachedMax: false,
            currentPage: 0,
            isLoading: false,
          );
          return;
        }
        if (currentState is ChatRecordSuccess && !currentState.isLoading) {
          yield currentState.copyWith(isLoading: true);
          var chatSessions =
              await chatRecordRepository.getChatRecords(event.sessionId);
          if (chatSessions == null || chatSessions.length > 0) {
            yield currentState.copyWith(hasReachedMax: true);
          } else {
            yield currentState.copyWith(
              currentPage: currentState.currentPage + 1,
              chatRecords: currentState.chatRecords + chatSessions,
            );
          }
        }
      } catch (_) {
        yield ChatRecordFailure();
      }
    }
    if (event is ChatRecordAdd) {
      if (currentState is ChatRecordSuccess) {
        currentState.chatRecords.insert(0, event.chatRecord);
        yield currentState;
      }
    }
  }

  bool _hasReachedMax(ChatRecordState state) =>
      state is ChatRecordSuccess && state.hasReachedMax;
}
