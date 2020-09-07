import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/article/output/article_page_result_entity.dart';
import 'package:uuid/uuid.dart';

class Article extends Equatable {
  final String id;
  final String title;
  final String nickName;
  final String abstracts;
  final List<String> tags;
  final int thumbings;
  final int comments;

  const Article(
      {this.id,
      this.title,
      this.nickName,
      this.abstracts,
      this.tags,
      this.thumbings,
      this.comments});

  static ArticlePageResultItem getArticle() {
    return ArticlePageResultItem(
        id: Uuid().v4(),
        title: "献给面临选择的你",
        abstracts:
            "人生海海，本就各有解答。风不会只吹往同一个方向，如果缺少一点运气，那就加上一些勇气。去选择，去行动，去发现更大的世界。即使我们一生都没有成为巨浪，也能各自奔涌自成流向。成为一种，两种，甚至所有可能。当你选择，就是答案",
        tagIds: ["选择", "人生感悟", "勇气"],
        thumbingNum: 1199,
        commentsNum: 253,
        createTime:DateTime(2020,9,4));
  }

  @override
  List<Object> get props =>
      [id, title, nickName, abstracts, tags, thumbings, comments];
}
