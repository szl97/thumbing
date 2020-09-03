import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PublishMomentsInputEntity with JsonConvert<PublishMomentsInputEntity> {
	String content;
	List<String> graphIds;
	List<String> tagIds;
	String title;
}
