import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';

momentsPageResultEntityFromJson(MomentsPageResultEntity data, Map<String, dynamic> json) {
	if (json['items'] != null) {
		data.items = new List<MomantsPageResultItems>();
		(json['items'] as List).forEach((v) {
			data.items.add(new MomantsPageResultItems().fromJson(v));
		});
	}
	if (json['position'] != null) {
		data.position = json['position']?.toInt();
	}
	if (json['totalCount'] != null) {
		data.totalCount = json['totalCount']?.toString();
	}
	return data;
}

Map<String, dynamic> momentsPageResultEntityToJson(MomentsPageResultEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	if (entity.items != null) {
		data['items'] =  entity.items.map((v) => v.toJson()).toList();
	}
	data['position'] = entity.position;
	data['totalCount'] = entity.totalCount;
	return data;
}

momantsPageResultItemsFromJson(MomantsPageResultItems data, Map<String, dynamic> json) {
	if (json['commentsNum'] != null) {
		data.commentsNum = json['commentsNum']?.toInt();
	}
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if(json['createTime'] != null){
		data.createTime = DateTime.tryParse(json['createTime']);
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['tagIds'] != null) {
		data.tagIds = json['tagIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['thumbUserIds'] != null) {
		data.thumbUserIds = json['thumbUserIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['thumbingNum'] != null) {
		data.thumbingNum = json['thumbingNum']?.toInt();
	}
	if (json['title'] != null) {
		data.title = json['title']?.toString();
	}
	if (json['userId'] != null) {
		data.userId = json['userId']?.toString();
	}
	return data;
}

Map<String, dynamic> momantsPageResultItemsToJson(MomantsPageResultItems entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['commentsNum'] = entity.commentsNum;
	data['content'] = entity.content;
	data['createTime'] = entity.createTime?.toString();
	data['id'] = entity.id;
	data['tagIds'] = entity.tagIds;
	data['thumbUserIds'] = entity.thumbUserIds;
	data['thumbingNum'] = entity.thumbingNum;
	data['title'] = entity.title;
	data['userId'] = entity.userId;
	return data;
}