part of '../../bloc/content/thumb_bloc.dart';

abstract class ThumbState extends Equatable {
  const ThumbState();
  @override
  List<Object> get props => [];
}

class ThumbInitial extends ThumbState {}

class ThumbInitialFinished extends ThumbState{
  const ThumbInitialFinished({this.id, this.thumbsNum, this.isThumb});
  final String id;
  final int thumbsNum;
  final bool isThumb;
  @override
  // TODO: implement props
  List<Object> get props => [id, thumbsNum, isThumb];
}
