import 'package:thumbing/generated/json/base/json_convert_content.dart';

class LoginInputEntity with JsonConvert<LoginInputEntity> {
	LoginInputEntity({this.userName, this.password});
	String userName;
	String password;
}
