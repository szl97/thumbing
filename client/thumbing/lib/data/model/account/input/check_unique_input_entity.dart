import 'package:thumbing/generated/json/base/json_convert_content.dart';

class CheckUniqueInputEntity with JsonConvert<CheckUniqueInputEntity> {
	String data;
	//Phone, Email, UserName
	String type;
}
