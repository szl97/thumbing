import 'package:thumbing/data/model/content/moments/input/thumb_moments_input_entity.dart';

thumbMomentsInputEntityFromJson(ThumbMomentsInputEntity data, Map<String, dynamic> json) {
	if (json['add'] != null) {
		data.add = json['add'];
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> thumbMomentsInputEntityToJson(ThumbMomentsInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['add'] = entity.add;
	data['id'] = entity.id;
	return data;
}