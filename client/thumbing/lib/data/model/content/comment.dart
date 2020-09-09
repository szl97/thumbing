import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class InnerComment extends Equatable {
  //final int id;
  /*
  * 发送方Id
  */
  final String fromId;
  /*
  * 接受方Id
  */
  final String toId;
  final bool isThumb;
  /*
  * 评论下的评论
  */
  final List<InnerComment> innerComments;
  /*
  * 发送方名字
  */
  final String fromName;
  /*
  * 接受方名字
  */
  final String toName;
  /*
  * 内容
  */
  final String content;
  /*
  * 点赞用户Id
  */
  final int thumbings;
  /*
  * 时间
  */
  final DateTime dateTime;
  /*
   *多久前发送 
   */
  final String howLongBefore;
  /*
  * 是否是第一层
  */
  final bool isParent;
  final commentId;

  const InnerComment(
      {this.fromId,this.isThumb,this.commentId,
      this.fromName,
      this.toId,
      this.toName,
      this.content,
      this.dateTime,
      this.howLongBefore,
      this.innerComments,
      this.isParent,
      this.thumbings});

  @override
  List<Object> get props => [
        isThumb,
        fromId,
        fromName,
        toId,
        toName,
        content,
        dateTime,
        howLongBefore,
        innerComments,
        isParent,
        thumbings,
    commentId
      ];

  static InnerComment getChildComment(int i) {
    String fromName = "猎户座的侠客";
    String toName = "天狼星的侠客";
    if (i % 2 == 0) {
      var temp = toName;
      toName = fromName;
      fromName = temp;
    }
    return InnerComment(
      commentId: Uuid().v4(),
      fromName: fromName,
      toName: toName,
      isParent: false,
      isThumb: false,
      howLongBefore: "一天前",
      thumbings: 25,
      content: "评论测试水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水啊啊啊啊",
    );
  }

  static InnerComment getComment() {
    String fromName = "猎户座的侠客";
    String toName = "天狼星的侠客";
    return InnerComment(
      commentId: Uuid().v4(),
      fromName: fromName,
      toName: toName,
      isParent: true,
      isThumb: false,
      howLongBefore: "一天前",
      thumbings: 25,
      content: "评论测试水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水水啊啊啊啊",
      innerComments: getChildList(),
    );
  }

  static List<InnerComment> getChildList() {
    return List.generate(8, (index) => getChildComment(index));
  }

  static List<InnerComment> getList() {
    return List.generate(20, (index) => getComment());
  }
}
