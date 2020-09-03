import 'package:thumbing/data/model/roast/output/roast_page_result_entity.dart';

roastPageResultEntityFromJson(RoastPageResultEntity data, Map<String, dynamic> json) {
	if (json['items'] != null) {
		data.items = new List<RoastPageResultItem>();
		(json['items'] as List).forEach((v) {
			data.items.add(new RoastPageResultItem().fromJson(v));
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

Map<String, dynamic> roastPageResultEntityToJson(RoastPageResultEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	if (entity.items != null) {
		data['items'] =  entity.items.map((v) => v.toJson()).toList();
	}
	data['position'] = entity.position;
	data['totalCount'] = entity.totalCount;
	return data;
}

roastPageResultItemFromJson(RoastPageResultItem data, Map<String, dynamic> json) {
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if (json['createTime'] != null) {
		data.createTime = DateTime.parse(json['createTime']?.toString());
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
	if (json['userId'] != null) {
		data.userId = json['userId']?.toString();
	}
	return data;
}

Map<String, dynamic> roastPageResultItemToJson(RoastPageResultItem entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['content'] = entity.content;
	data['createTime'] = entity.createTime;
	data['id'] = entity.id;
	data['thumbUserIds'] = entity.thumbUserIds;
	data['thumbingNum'] = entity.thumbingNum;
	data['userId'] = entity.userId;
	return data;
}