import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';

abstract class MomentState extends Equatable {
  const MomentState();

  @override
  List<Object> get props => [];
}

class MomentsInitial extends MomentState {}

class MomentsFailure extends MomentState {}

class MomentSuccess extends MomentState {
  final int currentPage;
  final int position;
  final List<MomentsPageResultItems> moments;
  final bool hasReachedMax;
  final bool isLoading;

  const MomentSuccess(
      {this.currentPage, this.moments, this.hasReachedMax, this.isLoading, this.position});

  MomentSuccess copyWith(
      {List<MomentsPageResultItems> moments,
      int position,
      bool hasReachedMax,
      int currentPage,
      bool isLoading}) {
    return MomentSuccess(
        position: position?? this.position,
        currentPage: currentPage ?? this.currentPage,
        moments: moments ?? this.moments,
        hasReachedMax: hasReachedMax ?? this.hasReachedMax,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props => [moments, hasReachedMax, currentPage, isLoading, position];
}
