import 'package:thumbing/data/model/account/input/sign_up_input_entity.dart';

signUpInputEntityFromJson(SignUpInputEntity data, Map<String, dynamic> json) {
	if (json['deviceInput'] != null) {
		data.deviceInput = new SignUpInputDeviceInput().fromJson(json['deviceInput']);
	}
	if (json['email'] != null) {
		data.email = json['email']?.toString();
	}
	if (json['password'] != null) {
		data.password = json['password']?.toString();
	}
	if (json['phoneNum'] != null) {
		data.phoneNum = json['phoneNum']?.toString();
	}
	if (json['userName'] != null) {
		data.userName = json['userName']?.toString();
	}
	if (json['validation'] != null) {
		data.validation = json['validation']?.toString();
	}
	return data;
}

Map<String, dynamic> signUpInputEntityToJson(SignUpInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	if (entity.deviceInput != null) {
		data['deviceInput'] = entity.deviceInput.toJson();
	}
	data['email'] = entity.email;
	data['password'] = entity.password;
	data['phoneNum'] = entity.phoneNum;
	data['userName'] = entity.userName;
	data['validation'] = entity.validation;
	return data;
}

signUpInputDeviceInputFromJson(SignUpInputDeviceInput data, Map<String, dynamic> json) {
	if (json['deviceId'] != null) {
		data.deviceId = json['deviceId']?.toString();
	}
	if (json['deviceInfo'] != null) {
		data.deviceInfo = json['deviceInfo']?.toString();
	}
	if (json['deviceName'] != null) {
		data.deviceName = json['deviceName']?.toString();
	}
	return data;
}

Map<String, dynamic> signUpInputDeviceInputToJson(SignUpInputDeviceInput entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['deviceId'] = entity.deviceId;
	data['deviceInfo'] = entity.deviceInfo;
	data['deviceName'] = entity.deviceName;
	return data;
}