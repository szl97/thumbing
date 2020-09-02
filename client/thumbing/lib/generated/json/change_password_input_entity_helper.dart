import 'package:thumbing/data/model/account/input/change_password_input_entity.dart';

changePasswordInputEntityFromJson(ChangePasswordInputEntity data, Map<String, dynamic> json) {
	if (json['oldPassWord'] != null) {
		data.oldPassWord = json['oldPassWord']?.toString();
	}
	if (json['password'] != null) {
		data.password = json['password']?.toString();
	}
	if (json['userId'] != null) {
		data.userId = json['userId']?.toString();
	}
	if (json['userName'] != null) {
		data.userName = json['userName']?.toString();
	}
	if (json['validation'] != null) {
		data.validation = json['validation']?.toString();
	}
	return data;
}

Map<String, dynamic> changePasswordInputEntityToJson(ChangePasswordInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['oldPassWord'] = entity.oldPassWord;
	data['password'] = entity.password;
	data['userId'] = entity.userId;
	data['userName'] = entity.userName;
	data['validation'] = entity.validation;
	return data;
}