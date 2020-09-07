import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/roast/output/roast_page_result_entity.dart';

abstract class RoastState extends Equatable {
  const RoastState();

  @override
  List<Object> get props => [];
}

class RoastInitial extends RoastState {}

class RoastFailure extends RoastState {}

class RoastSuccess extends RoastState {
  final int currentPage;
  final int currentPosition;
  final List<RoastPageResultItem> roasts;
  final bool hasReachedMax;
  final bool isLoading;

  const RoastSuccess(
      {this.currentPage,
      this.currentPosition,
      this.roasts,
      this.hasReachedMax,
      this.isLoading});

  RoastSuccess copyWith(
      {List<RoastPageResultItem> roasts,
      int currentPosition,
      bool hasReachedMax,
      int currentPage,
      bool isLoading}) {
    return RoastSuccess(
        currentPosition: currentPosition ?? this.currentPosition,
        currentPage: currentPage ?? this.currentPage,
        roasts: roasts ?? this.roasts,
        hasReachedMax: hasReachedMax ?? this.hasReachedMax,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props =>
      [roasts, hasReachedMax, currentPage, isLoading, currentPosition];
}
