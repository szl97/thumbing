import 'package:thumbing/data/model/account/input/check_unique_input_entity.dart';

checkUniqueInputEntityFromJson(CheckUniqueInputEntity data, Map<String, dynamic> json) {
	if (json['data'] != null) {
		data.data = json['data']?.toString();
	}
	if (json['type'] != null) {
		data.type = json['type']?.toString();
	}
	return data;
}

Map<String, dynamic> checkUniqueInputEntityToJson(CheckUniqueInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['data'] = entity.data;
	data['type'] = entity.type;
	return data;
}