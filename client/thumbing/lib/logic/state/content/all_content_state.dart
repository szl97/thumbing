import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/all_content.dart';

abstract class AllContentState extends Equatable {
  const AllContentState();

  @override
  List<Object> get props => [];
}

class AllContentInitial extends AllContentState {}

class AllContentFailure extends AllContentState {}

class AllContentSuccess extends AllContentState {
  final AllContent allContent;

  const AllContentSuccess({this.allContent});

  AllContentSuccess copyWith({AllContent allContent}) {
    return AllContentSuccess(allContent: allContent ?? this.allContent);
  }

  @override
  List<Object> get props => [allContent];
}
