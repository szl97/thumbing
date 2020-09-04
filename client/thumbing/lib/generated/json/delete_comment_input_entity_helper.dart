import 'package:thumbing/data/model/content/comment/input/delete_comment_input_entity.dart';

deleteCommentInputEntityFromJson(DeleteCommentInputEntity data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['contentId'] != null) {
		data.contentId = json['contentId']?.toString();
	}
	if (json['contentType'] != null) {
		data.contentType = json['contentType']?.toString();
	}
	return data;
}

Map<String, dynamic> deleteCommentInputEntityToJson(DeleteCommentInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['contentId'] = entity.contentId;
	data['contentType'] = entity.contentType;
	return data;
}