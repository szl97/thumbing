import 'package:thumbing/generated/json/base/json_convert_content.dart';

class SearchResultEntity with JsonConvert<SearchResultEntity> {
	String content;
	String contentType;
	DateTime createTime;
	String id;
	List<String> tagIds;
	String title;
	String userId;
}
