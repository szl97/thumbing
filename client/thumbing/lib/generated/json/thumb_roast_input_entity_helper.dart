import 'package:thumbing/data/model/roast/input/thumb_roast_input_entity.dart';

thumbRoastInputEntityFromJson(ThumbRoastInputEntity data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> thumbRoastInputEntityToJson(ThumbRoastInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	return data;
}