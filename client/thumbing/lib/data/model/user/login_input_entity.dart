import 'package:thumbing/generated/json/base/json_convert_content.dart';

class LoginInputEntity with JsonConvert<LoginInputEntity> {
	String userName;
	String password;
}
