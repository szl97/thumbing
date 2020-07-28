import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class Moments extends Equatable {
  String id;
  String nickName;
  String abstracts;
  List<String> tags;
  int thumbings;
  int comments;
  Moments() {
    id = Uuid().v4();
    nickName = "天狼星的侠客";
    abstracts =
        "献给面临选择的你：人生海海，本就各有解答。风不会只吹往同一个方向，如果缺少一点运气，那就加上一些勇气。去选择，去行动，去发现更大的世界。即使我们一生都没有成为巨浪，也能各自奔涌自成流向。成为一种，两种，甚至所有可能。当你选择，就是答案";
    tags = ["选择", "人生感悟", "勇气"];
    thumbings = 1199;
    comments = 253;
  }

  @override
  List<Object> get props =>
      [id, nickName, abstracts, tags, thumbings, comments];
}
