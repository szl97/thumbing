import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';

abstract class MomentsEvent extends Equatable {
  const MomentsEvent();
  @override
  List<Object> get props => [];
}

class MomentsFetched extends MomentsEvent {}

class MomentsRefresh extends MomentsEvent {}

class AddMomentsThumb extends MomentsEvent{
  const AddMomentsThumb(this.id, this.index) : super();
  final String id;
  final int index;
  @override
  List<Object> get props => [id];
}

class CancelMomentsThumb extends MomentsEvent{
  const CancelMomentsThumb(this.id, this.index) : super();
  final String id;
  final int index;
  @override
  List<Object> get props => [id];
}