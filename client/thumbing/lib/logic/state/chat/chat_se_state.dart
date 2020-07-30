import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/chat/chat_session.dart';

abstract class ChatSessionState extends Equatable {
  const ChatSessionState();

  @override
  List<Object> get props => [];
}

class ChatSessionInitial extends ChatSessionState {}

class ChatSessionSuccess extends ChatSessionState {
  final List<ChatSession> chatSessions;
  final bool hasReachedMax;
  final bool isLoading;
  final int currentPage;
  const ChatSessionSuccess(
      {this.chatSessions,
      this.isLoading,
      this.hasReachedMax,
      this.currentPage});
  @override
  List<Object> get props =>
      [chatSessions, isLoading, hasReachedMax, currentPage];
  ChatSessionSuccess copyWith(
      {List<ChatSession> chatSessions,
      bool hasReachedMax,
      bool isLoading,
      int currentPage}) {
    return ChatSessionSuccess(
      chatSessions: chatSessions ?? this.chatSessions,
      hasReachedMax: hasReachedMax ?? this.hasReachedMax,
      isLoading: isLoading ?? this.isLoading,
      currentPage: currentPage ?? this.currentPage,
    );
  }
}

class ChatSessionFailure extends ChatSessionState {}
