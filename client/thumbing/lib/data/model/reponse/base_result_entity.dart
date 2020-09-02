import 'package:thumbing/generated/json/base/json_convert_content.dart';

class BaseResultEntity with JsonConvert<BaseResultEntity> {
	int code;
	String message;
	dynamic data;
	bool success;
}
