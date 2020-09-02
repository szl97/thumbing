import 'package:thumbing/data/model/reponse/base_result_entity.dart';

baseResultEntityFromJson(BaseResultEntity data, Map<String, dynamic> json) {
	if (json['code'] != null) {
		data.code = json['code']?.toInt();
	}
	if (json['message'] != null) {
		data.message = json['message']?.toString();
	}
	if (json['data'] != null) {
		data.data = json['data'];
	}
	if (json['success'] != null) {
		data.success = json['success'];
	}
	return data;
}

Map<String, dynamic> baseResultEntityToJson(BaseResultEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['code'] = entity.code;
	data['message'] = entity.message;
	data['data'] = entity.data;
	data['success'] = entity.success;
	return data;
}