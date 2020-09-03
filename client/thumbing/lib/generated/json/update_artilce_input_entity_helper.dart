import 'package:thumbing/data/model/content/article/input/update_artilce_input_entity.dart';

updateArtilceInputEntityFromJson(UpdateArtilceInputEntity data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> updateArtilceInputEntityToJson(UpdateArtilceInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	data['id'] = entity.id;
	return data;
}