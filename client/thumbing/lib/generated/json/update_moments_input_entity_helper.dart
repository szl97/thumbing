import 'package:thumbing/data/model/content/moments/input/update_moments_input_entity.dart';

updateMomentsInputEntityFromJson(UpdateMomentsInputEntity data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> updateMomentsInputEntityToJson(UpdateMomentsInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	data['id'] = entity.id;
	return data;
}