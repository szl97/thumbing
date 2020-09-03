import 'package:thumbing/generated/json/base/json_convert_content.dart';

class ArticlePageResultEntity with JsonConvert<ArticlePageResultEntity> {
	List<ArticlePageResultItem> items;
	int position;
	String totalCount;
}

class ArticlePageResultItem with JsonConvert<ArticlePageResultItem> {
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
}
