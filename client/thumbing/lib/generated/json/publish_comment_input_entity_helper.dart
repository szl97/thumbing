import 'package:thumbing/data/model/content/comment/input/publish_comment_input_entity.dart';

publishCommentInputEntityFromJson(PublishCommentInputEntity data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['contentId'] != null) {
		data.contentId = json['contentId']?.toString();
	}
	if (json['contentType'] != null) {
		data.contentType = json['contentType']?.toString();
	}
	if (json['fromNickName'] != null) {
		data.fromNickName = json['fromNickName']?.toString();
	}
	if (json['parentCommentId'] != null) {
		data.parentCommentId = json['parentCommentId']?.toString();
	}
	if (json['toNickName'] != null) {
		data.toNickName = json['toNickName']?.toString();
	}
	if (json['toUserId'] != null) {
		data.toUserId = json['toUserId']?.toString();
	}
	return data;
}

Map<String, dynamic> publishCommentInputEntityToJson(PublishCommentInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	data['contentId'] = entity.contentId;
	data['contentType'] = entity.contentType;
	data['fromNickName'] = entity.fromNickName;
	data['parentCommentId'] = entity.parentCommentId;
	data['toNickName'] = entity.toNickName;
	data['toUserId'] = entity.toUserId;
	return data;
}