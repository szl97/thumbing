import 'package:thumbing/generated/json/base/json_convert_content.dart';

class ChangePasswordInputEntity with JsonConvert<ChangePasswordInputEntity> {
	String oldPassWord;
	String password;
	String userId;
	String userName;
	String validation;
}
