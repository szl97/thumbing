import 'package:thumbing/data/model/user/check_auth_input_entity.dart';

checkAuthInputEntityFromJson(CheckAuthInputEntity data, Map<String, dynamic> json) {
	if (json['userName'] != null) {
		data.userName = json['userName']?.toString();
	}
	return data;
}

Map<String, dynamic> checkAuthInputEntityToJson(CheckAuthInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['userName'] = entity.userName;
	return data;
}