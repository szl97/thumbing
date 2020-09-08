import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

part '../../event/content/thumb_event.dart';
part '../../state/content/thumb_state.dart';

class ThumbBloc extends Bloc<ThumbEvent, ThumbState> {
  ThumbBloc() : super(ThumbInitial());

  @override
  Stream<ThumbState> mapEventToState(ThumbEvent event) async* {
    // TODO: implement mapEventToState
    if(event is SetThumbDetails){
      if(event.id != null){
        yield ThumbInitialFinished(id: event.id, thumbsNum: event.thumbsNum, isThumb: event.isThumb);
      }
    }
    if(event is AddThumb){
      yield ThumbInitialFinished(id: event.id, thumbsNum: event.thumbsNum + 1, isThumb: true);
    }
    if(event is CancelThumb){
      yield ThumbInitialFinished(id: event.id, thumbsNum: event.thumbsNum <= 0 ? 0 : event.thumbsNum - 1, isThumb: false);
    }
  }
}
