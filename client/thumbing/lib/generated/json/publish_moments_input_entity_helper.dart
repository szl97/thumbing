import 'package:thumbing/data/model/content/moments/input/publish_moments_input_entity.dart';

publishMomentsInputEntityFromJson(PublishMomentsInputEntity data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['graphIds'] != null) {
		data.graphIds = json['graphIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['tagIds'] != null) {
		data.tagIds = json['tagIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['title'] != null) {
		data.title = json['title']?.toString();
	}
	return data;
}

Map<String, dynamic> publishMomentsInputEntityToJson(PublishMomentsInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	data['graphIds'] = entity.graphIds;
	data['tagIds'] = entity.tagIds;
	data['title'] = entity.title;
	return data;
}