import 'package:thumbing/data/model/roast/input/publish_roast_input_entity.dart';

publishRoastInputEntityFromJson(PublishRoastInputEntity data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	return data;
}

Map<String, dynamic> publishRoastInputEntityToJson(PublishRoastInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	return data;
}