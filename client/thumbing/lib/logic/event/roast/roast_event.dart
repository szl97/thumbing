import 'package:equatable/equatable.dart';

abstract class RoastEvent extends Equatable {
  const RoastEvent();
  @override
  List<Object> get props => [];
}

class RoastFetched extends RoastEvent {}

class RoastThumbing extends RoastEvent {
  final String id;
  final String userId;
  const RoastThumbing({this.id, this.userId});
  @override
  List<Object> get props => [id, userId];
}

class NextRoast extends RoastEvent {
  final int position;
  const NextRoast({this.position});
  @override
  List<Object> get props => [position];
}
