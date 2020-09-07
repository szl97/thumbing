import 'package:equatable/equatable.dart';
import 'package:thumbing/generated/json/base/json_convert_content.dart';

class ArticlePageResultEntity with JsonConvert<ArticlePageResultEntity> {
	ArticlePageResultEntity({this.items, this.position});
	List<ArticlePageResultItem> items;
	int position;
	String totalCount;
}

class ArticlePageResultItem extends Equatable with JsonConvert<ArticlePageResultItem> {
	ArticlePageResultItem({this.id,this.tagIds,this.abstracts,this.createTime, this.graphIds,this.thumbingNum,this.thumbUserIds,this.title,this.userId,this.commentsNum,this.content});
	String abstracts;
	int commentsNum;
	String content;
	DateTime createTime;
	List<String> graphIds;
	String id;
	List<String> tagIds;
	List<String> thumbUserIds;
	int thumbingNum;
	String title;
	String userId;

  @override
  // TODO: implement props
  List<Object> get props => [id,tagIds,abstracts,createTime,graphIds,thumbingNum,thumbUserIds,title,userId,commentsNum,content];
}
