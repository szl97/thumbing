import 'dart:collection';

import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/chat/chat_record.dart';

abstract class ChatRecordState extends Equatable {
  const ChatRecordState();

  @override
  List<Object> get props => [];
}

class ChatRecordInitial extends ChatRecordState {}

class ChatRecordSuccess extends ChatRecordState {
  final List<ChatRecord> chatRecords;
  final bool hasReachedMax;
  final bool isLoading;
  final int currentPage;
  const ChatRecordSuccess(
      {this.chatRecords, this.isLoading, this.hasReachedMax, this.currentPage});

  ChatRecordSuccess copyWith(
      {List<ChatRecord> chatRecords,
      bool hasReachedMax,
      bool isLoading,
      int currentPage}) {
    return ChatRecordSuccess(
      chatRecords: chatRecords ?? this.chatRecords,
      hasReachedMax: hasReachedMax ?? this.hasReachedMax,
      isLoading: isLoading ?? this.isLoading,
      currentPage: currentPage ?? this.currentPage,
    );
  }

  @override
  List<Object> get props =>
      [chatRecords, isLoading, hasReachedMax, currentPage];
}

class ChatRecordFailure extends ChatRecordState {}
