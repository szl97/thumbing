import 'package:equatable/equatable.dart';
import 'package:thumbing/presentation/util/format_time_utils.dart';
import 'package:uuid/uuid.dart';

import 'output/roast_page_result_entity.dart';

class Roast extends Equatable {
  final String id;
  final String userId;
  final String content;
  final int thumbings;
  const Roast({this.id, this.content, this.userId, this.thumbings});

  static RoastPageResultItem getRoast() {
    return RoastPageResultItem(
      id: Uuid().v4(),
      content: "由于人帅又有才华，感觉活着太无聊，求安慰QAQ",
      thumbingNum: 129,
    );
  }

  @override
  List<Object> get props => [id];
}
