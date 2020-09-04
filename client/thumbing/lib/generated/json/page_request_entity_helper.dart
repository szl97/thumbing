import 'package:thumbing/data/model/request/page_request_entity.dart';

pageRequestEntityFromJson(PageRequestEntity data, Map<String, dynamic> json) {
	if (json['pageNumber'] != null) {
		data.pageNumber = json['pageNumber']?.toInt();
	}
	if (json['pageSize'] != null) {
		data.pageSize = json['pageSize']?.toInt();
	}
	if (json['position'] != null) {
		data.position = json['position']?.toInt();
	}
	return data;
}

Map<String, dynamic> pageRequestEntityToJson(PageRequestEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['pageNumber'] = entity.pageNumber;
	data['pageSize'] = entity.pageSize;
	data['position'] = entity.position;
	return data;
}