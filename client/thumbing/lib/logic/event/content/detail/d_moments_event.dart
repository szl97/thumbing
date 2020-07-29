import 'package:equatable/equatable.dart';

abstract class MomentsDetailEvent extends Equatable {
  final String id;
  const MomentsDetailEvent(this.id);
  @override
  List<Object> get props => [];
}

class MomentsDetailFetched extends MomentsDetailEvent {
  MomentsDetailFetched(String id) : super(id);
}

class MomentsDetailRefresh extends MomentsDetailEvent {
  MomentsDetailRefresh(String id) : super(id);
}
