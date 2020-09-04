import 'package:thumbing/data/model/content/search/input/search_input_entity.dart';

searchInputEntityFromJson(SearchInputEntity data, Map<String, dynamic> json) {
	if (json['pageNumber'] != null) {
		data.pageNumber = json['pageNumber']?.toInt();
	}
	if (json['pageSize'] != null) {
		data.pageSize = json['pageSize']?.toInt();
	}
	if (json['keyword'] != null) {
		data.keyword = json['keyword']?.toString();
	}
	return data;
}

Map<String, dynamic> searchInputEntityToJson(SearchInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['pageNumber'] = entity.pageNumber;
	data['pageSize'] = entity.pageSize;
	data['keyword'] = entity.keyword;
	return data;
}