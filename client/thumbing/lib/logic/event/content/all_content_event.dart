import 'package:equatable/equatable.dart';

abstract class AllContentEvent extends Equatable {
  const AllContentEvent();
  @override
  List<Object> get props => [];
}

class AllContentFetched extends AllContentEvent {}
