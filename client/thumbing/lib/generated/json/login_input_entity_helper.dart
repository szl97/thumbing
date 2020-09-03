import 'package:thumbing/data/model/user/login_input_entity.dart';

loginInputEntityFromJson(LoginInputEntity data, Map<String, dynamic> json) {
	if (json['userName'] != null) {
		data.userName = json['userName']?.toString();
	}
	if (json['password'] != null) {
		data.password = json['password']?.toString();
	}
	return data;
}

Map<String, dynamic> loginInputEntityToJson(LoginInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['userName'] = entity.userName;
	data['password'] = entity.password;
	return data;
}