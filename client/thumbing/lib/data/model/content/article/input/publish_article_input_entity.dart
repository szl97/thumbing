import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PublishArticleInputEntity with JsonConvert<PublishArticleInputEntity> {
	String content;
	List<String> graphIds;
	List<String> tagIds;
	String title;
}
