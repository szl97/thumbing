import 'package:thumbing/data/model/request/id_request_entity.dart';

idRequestEntityFromJson(IdRequestEntity data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> idRequestEntityToJson(IdRequestEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	return data;
}