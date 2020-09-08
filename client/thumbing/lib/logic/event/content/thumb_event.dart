part of '../../bloc/content/thumb_bloc.dart';

abstract class ThumbEvent extends Equatable {
  const ThumbEvent({this.id, this.thumbsNum, this.isThumb});
  final String id;
  final int thumbsNum;
  final bool isThumb;
  @override
  // TODO: implement props
  List<Object> get props => [id, thumbsNum, isThumb];
}

class SetThumbDetails extends ThumbEvent{
  SetThumbDetails({id, thumbsNum, isThumb}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb);
}

class AddThumb extends ThumbEvent{
  AddThumb({id, thumbsNum, isThumb}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb);
}

class CancelThumb extends ThumbEvent{
  CancelThumb({id, thumbsNum, isThumb}) : super(id: id, thumbsNum: thumbsNum, isThumb: isThumb);
}