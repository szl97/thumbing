import 'package:thumbing/data/model/content/comment/input/fetch_comments_input_entity.dart';

fetchCommentsInputEntityFromJson(FetchCommentsInputEntity data, Map<String, dynamic> json) {
	if (json['contentId'] != null) {
		data.contentId = json['contentId']?.toString();
	}
	if (json['contentType'] != null) {
		data.contentType = json['contentType']?.toString();
	}
	return data;
}

Map<String, dynamic> fetchCommentsInputEntityToJson(FetchCommentsInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['contentId'] = entity.contentId;
	data['contentType'] = entity.contentType;
	return data;
}