import 'package:thumbing/data/model/content/search/output/search_result_entity.dart';

searchResultEntityFromJson(SearchResultEntity data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['contentType'] != null) {
		data.contentType = json['contentType']?.toString();
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
	if (json['title'] != null) {
		data.title = json['title']?.toString();
	}
	if (json['userId'] != null) {
		data.userId = json['userId']?.toString();
	}
	return data;
}

Map<String, dynamic> searchResultEntityToJson(SearchResultEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	data['contentType'] = entity.contentType;
	data['createTime'] = entity.createTime?.toString();
	data['id'] = entity.id;
	data['tagIds'] = entity.tagIds;
	data['title'] = entity.title;
	data['userId'] = entity.userId;
	return data;
}