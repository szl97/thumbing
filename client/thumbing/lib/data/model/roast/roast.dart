import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class Roast extends Equatable {
  final String id;
  final String userId;
  final String content;
  final int thumbings;
  const Roast({this.id, this.content, this.userId, this.thumbings});

  static Roast getRoast() {
    return Roast(
      id: Uuid().v4(),
      content: "由于人帅又有才华，感觉活着太无聊，求安慰QAQ",
      thumbings: 129,
    );
  }

  @override
  List<Object> get props => [id];
}
