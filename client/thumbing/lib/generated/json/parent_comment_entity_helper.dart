import 'package:thumbing/data/model/content/comment/output/parent_comment_entity.dart';

parentCommentEntityFromJson(ParentCommentEntity data, Map<String, dynamic> json) {
	if (json['childComments'] != null) {
		data.childComments = new List<ParentCommantChildCommants>();
		(json['childComments'] as List).forEach((v) {
			data.childComments.add(new ParentCommantChildCommants().fromJson(v));
		});
	}
	if (json['commentId'] != null) {
		data.commentId = json['commentId']?.toString();
	}
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['contentId'] != null) {
		data.contentId = json['contentId']?.toString();
	}
	if (json['contentType'] != null) {
		data.contentType = json['contentType']?.toString();
	}
	if(json['createTime'] != null){
		data.createTime = DateTime.tryParse(json['createTime']);
	}
	if (json['fromNickName'] != null) {
		data.fromNickName = json['fromNickName']?.toString();
	}
	if (json['fromUserId'] != null) {
		data.fromUserId = json['fromUserId']?.toString();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['thumbUserIds'] != null) {
		data.thumbUserIds = json['thumbUserIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['thumbingNum'] != null) {
		data.thumbingNum = json['thumbingNum']?.toInt();
	}
	return data;
}

Map<String, dynamic> parentCommentEntityToJson(ParentCommentEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	if (entity.childComments != null) {
		data['childComments'] =  entity.childComments.map((v) => v.toJson()).toList();
	}
	data['commentId'] = entity.commentId;
	data['content'] = entity.content;
	data['contentId'] = entity.contentId;
	data['contentType'] = entity.contentType;
	data['createTime'] = entity.createTime?.toString();
	data['fromNickName'] = entity.fromNickName;
	data['fromUserId'] = entity.fromUserId;
	data['id'] = entity.id;
	data['thumbUserIds'] = entity.thumbUserIds;
	data['thumbingNum'] = entity.thumbingNum;
	return data;
}

parentCommantChildCommantsFromJson(ParentCommantChildCommants data, Map<String, dynamic> json) {
	if (json['commentId'] != null) {
		data.commentId = json['commentId']?.toString();
	}
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['createTime'] != null) {
		data.createTime = json['createTime']?.toString();
	}
	if (json['fromNickName'] != null) {
		data.fromNickName = json['fromNickName']?.toString();
	}
	if (json['fromUserId'] != null) {
		data.fromUserId = json['fromUserId']?.toString();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['parentCommentId'] != null) {
		data.parentCommentId = json['parentCommentId']?.toString();
	}
	if (json['thumbUserIds'] != null) {
		data.thumbUserIds = json['thumbUserIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['thumbingNum'] != null) {
		data.thumbingNum = json['thumbingNum']?.toInt();
	}
	if (json['toNickName'] != null) {
		data.toNickName = json['toNickName']?.toString();
	}
	if (json['toUserId'] != null) {
		data.toUserId = json['toUserId']?.toString();
	}
	return data;
}

Map<String, dynamic> parentCommantChildCommantsToJson(ParentCommantChildCommants entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['commentId'] = entity.commentId;
	data['content'] = entity.content;
	data['createTime'] = entity.createTime;
	data['fromNickName'] = entity.fromNickName;
	data['fromUserId'] = entity.fromUserId;
	data['id'] = entity.id;
	data['parentCommentId'] = entity.parentCommentId;
	data['thumbUserIds'] = entity.thumbUserIds;
	data['thumbingNum'] = entity.thumbingNum;
	data['toNickName'] = entity.toNickName;
	data['toUserId'] = entity.toUserId;
	return data;
}