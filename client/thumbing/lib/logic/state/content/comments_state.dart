import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';

abstract class CommentsState extends Equatable {
  const CommentsState();

  @override
  List<Object> get props => [];
}

class CommentsInitial extends CommentsState {}

class CommentsFailure extends CommentsState {}

class CommentsSuccess extends CommentsState {
  final MomentsDetail momentsDetail;
  final bool isLoading;

  const CommentsSuccess({this.momentsDetail, this.isLoading});

  CommentsSuccess copyWith({MomentsDetail momentsDetail, bool isLoading}) {
    return CommentsSuccess(
        momentsDetail: momentsDetail ?? this.momentsDetail,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props => [momentsDetail, isLoading];
}
