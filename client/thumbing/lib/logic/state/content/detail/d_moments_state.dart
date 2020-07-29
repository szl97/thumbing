import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';

abstract class MomentsDetailState extends Equatable {
  const MomentsDetailState();

  @override
  List<Object> get props => [];
}

class MomentsDetailInitial extends MomentsDetailState {}

class MomentsDetailFailure extends MomentsDetailState {}

class MomentsDetailSuccess extends MomentsDetailState {
  final MomentsDetail momentsDetail;
  final bool isLoading;

  const MomentsDetailSuccess({this.momentsDetail, this.isLoading});

  MomentsDetailSuccess copyWith({MomentsDetail momentsDetail, bool isLoading}) {
    return MomentsDetailSuccess(
        momentsDetail: momentsDetail ?? this.momentsDetail,
        isLoading: isLoading ?? this.isLoading);
  }

  @override
  List<Object> get props => [momentsDetail, isLoading];
}
