import 'package:bloc/bloc.dart';
import 'package:thumbing/data/repository/content/all_content_rep.dart';
import 'package:thumbing/logic/event/content/all_content_event.dart';
import 'package:thumbing/logic/state/content/all_content_state.dart';

class AllContentBloc extends Bloc<AllContentEvent, AllContentState> {
  AllContentRepository allContentRepository;

  AllContentBloc() : super(AllContentInitial()) {
    allContentRepository = AllContentRepository();
  }

  @override
  Stream<AllContentState> mapEventToState(AllContentEvent event) async* {
    final currentState = state;
    if (event is AllContentFetched) {
      try {
        if (currentState is AllContentInitial) {
          final allContent = await allContentRepository.getAllContent();
          yield AllContentSuccess(allContent: allContent);
          return;
        }
        if (currentState is AllContentSuccess) {
          yield state;
        }
      } catch (_) {
        yield AllContentFailure();
      }
    }
  }
}
