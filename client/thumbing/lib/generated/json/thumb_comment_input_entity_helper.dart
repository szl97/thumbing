import 'package:thumbing/data/model/content/comment/input/thumb_comment_input_entity.dart';

thumbCommentInputEntityFromJson(ThumbCommentInputEntity data, Map<String, dynamic> json) {
	if (json['add'] != null) {
		data.add = json['add'];
	}
	if (json['contentId'] != null) {
		data.contentId = json['contentId']?.toString();
	}
	if (json['contentType'] != null) {
		data.contentType = json['contentType']?.toString();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> thumbCommentInputEntityToJson(ThumbCommentInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['add'] = entity.add;
	data['contentId'] = entity.contentId;
	data['contentType'] = entity.contentType;
	data['id'] = entity.id;
	return data;
}