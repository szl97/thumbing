import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';

abstract class MomentsEvent extends Equatable {
  const MomentsEvent();
  @override
  List<Object> get props => [];
}

class MomentsFetched extends MomentsEvent {}

class MomentsRefresh extends MomentsEvent {}

class MomentsInitialSuccess extends MomentsEvent {
  final List<MomentsPageResultItems> moments;
  const MomentsInitialSuccess({this.moments}) : super();
  @override
  List<Object> get props => [moments];
}

class MomentsInitialFailed extends MomentsEvent {}
