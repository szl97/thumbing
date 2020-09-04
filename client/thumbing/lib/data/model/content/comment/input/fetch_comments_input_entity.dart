import 'package:thumbing/generated/json/base/json_convert_content.dart';

class FetchCommentsInputEntity with JsonConvert<FetchCommentsInputEntity> {
	String contentId;
	String contentType;
}
