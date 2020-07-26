import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/moments.dart';

abstract class MomentState extends Equatable {
  const MomentState();

  @override
  List<Object> get props => [];
}

class MomentsInitial extends MomentState {}

class MomentsFailure extends MomentState {}

class MomentSuccess extends MomentState {
  final int currentPage;
  final List<Moments> moments;
  final bool hasReachedMax;
  final bool isLoading;

  const MomentSuccess(
      {this.currentPage, this.moments, this.hasReachedMax, this.isLoading});

  MomentSuccess copyWith(
      {List<Moments> moments,
      bool hasReachedMax,
      int currentPage,
      bool isLoading}) {
    return MomentSuccess(
        currentPage: currentPage ?? this.currentPage,
        moments: moments ?? this.moments,
        hasReachedMax: hasReachedMax ?? this.hasReachedMax,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props => [moments, hasReachedMax, currentPage, isLoading];
}
