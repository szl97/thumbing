import 'package:thumbing/generated/json/base/json_convert_content.dart';

class SignUpInputEntity with JsonConvert<SignUpInputEntity> {
	SignUpInputDeviceInput deviceInput;
	String email;
	String password;
	String phoneNum;
	String userName;
	String validation;
}

class SignUpInputDeviceInput with JsonConvert<SignUpInputDeviceInput> {
	String deviceId;
	String deviceInfo;
	String deviceName;
}
