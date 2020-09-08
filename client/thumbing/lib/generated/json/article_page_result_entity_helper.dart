import 'package:thumbing/data/model/content/article/output/article_page_result_entity.dart';

articlePageResultEntityFromJson(ArticlePageResultEntity data, Map<String, dynamic> json) {
	if (json['items'] != null) {
		data.items = new List<ArticlePageResultItem>();
		(json['items'] as List).forEach((v) {
			data.items.add(new ArticlePageResultItem().fromJson(v));
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

Map<String, dynamic> articlePageResultEntityToJson(ArticlePageResultEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	if (entity.items != null) {
		data['items'] =  entity.items.map((v) => v.toJson()).toList();
	}
	data['position'] = entity.position;
	data['totalCount'] = entity.totalCount;
	return data;
}

articlePageResultItemFromJson(ArticlePageResultItem data, Map<String, dynamic> json) {
	if (json['abstracts'] != null) {
		data.abstracts = json['abstracts']?.toString();
	}
	if (json['commentsNum'] != null) {
		data.commentsNum = json['commentsNum']?.toInt();
	}
	if (json['content'] != null) {
		data.content = json['content']?.toString();
	}
	if(json['createTime'] != null){
		data.createTime = DateTime.tryParse(json['createTime']);
	}
	if (json['graphIds'] != null) {
		data.graphIds = json['graphIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['tagIds'] != null) {
		data.tagIds = json['tagIds']?.map((v) => v?.toString())?.toList()?.cast<String>();
	}
	if (json['isThumb'] != null) {
		data.isThumb = json['isThumb'];
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

Map<String, dynamic> articlePageResultItemToJson(ArticlePageResultItem entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['abstracts'] = entity.abstracts;
	data['commentsNum'] = entity.commentsNum;
	data['content'] = entity.content;
	data['createTime'] = entity.createTime?.toString();
	data['graphIds'] = entity.graphIds;
	data['id'] = entity.id;
	data['tagIds'] = entity.tagIds;
	data['isThumb'] = entity.isThumb;
	data['thumbingNum'] = entity.thumbingNum;
	data['title'] = entity.title;
	data['userId'] = entity.userId;
	return data;
}