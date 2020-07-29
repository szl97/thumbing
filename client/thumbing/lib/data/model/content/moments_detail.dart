import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/comment.dart';
import 'package:uuid/uuid.dart';

class MomentsDetail extends Equatable {
  final String id;
  final String nickName;
  final String content;
  final List<String> tags;
  final int thumbings;
  final List<InnerComment> innerComments;
  /*
  * 时间
  */
  final DateTime dateTime;
  /*
   *多久前发送 
   */
  final String howLongBefore;

  const MomentsDetail({
    this.id,
    this.nickName,
    this.content,
    this.tags,
    this.thumbings,
    this.innerComments,
    this.dateTime,
    this.howLongBefore,
  });

  static MomentsDetail getMomentsDetail() {
    return MomentsDetail(
      id: Uuid().v4(),
      nickName: "天狼星的侠客",
      content:
          "人生海海，本就各有解答。风不会只吹往同一个方向，如果缺少一点运气，那就加上一些勇气。去选择，去行动，去发现更大的世界。即使我们一生都没有成为巨浪，也能各自奔涌自成流向。成为一种，两种，甚至所有可能。当你选择，就是答案",
      tags: ["选择", "人生感悟", "勇气"],
      thumbings: 1199,
      innerComments: InnerComment.getList(),
      howLongBefore: "三天前",
    );
  }

  @override
  List<Object> get props => [
        id,
        nickName,
        content,
        tags,
        thumbings,
        innerComments,
        dateTime,
        howLongBefore
      ];
}
