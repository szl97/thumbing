import 'package:thumbing/generated/json/base/json_convert_content.dart';

class CheckAuthInputEntity with JsonConvert<CheckAuthInputEntity> {
	CheckAuthInputEntity({this.userName});
	String userName;
}
