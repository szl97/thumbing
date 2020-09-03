import 'package:thumbing/data/model/content/article/input/thumb_article_input_entity.dart';

thumbArticleInputEntityFromJson(ThumbArticleInputEntity data, Map<String, dynamic> json) {
	if (json['add'] != null) {
		data.add = json['add'];
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	return data;
}

Map<String, dynamic> thumbArticleInputEntityToJson(ThumbArticleInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['add'] = entity.add;
	data['id'] = entity.id;
	return data;
}