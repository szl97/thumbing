import 'package:thumbing/data/model/account/output/account_entity.dart';
import 'package:equatable/equatable.dart';

accountEntityFromJson(AccountEntity data, Map<String, dynamic> json) {
	if (json['access'] != null) {
		data.access = json['access'];
	}
	if (json['active'] != null) {
		data.active = json['active'];
	}
	if (json['continueDays'] != null) {
		data.continueDays = json['continueDays']?.toInt();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if(json['lastLogin'] != null){
		data.lastLogin = DateTime.tryParse(json['lastLogin']);
	}
	if (json['userName'] != null) {
		data.userName = json['userName']?.toString();
	}
	return data;
}

Map<String, dynamic> accountEntityToJson(AccountEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['access'] = entity.access;
	data['active'] = entity.active;
	data['continueDays'] = entity.continueDays;
	data['id'] = entity.id;
	data['lastLogin'] = entity.lastLogin?.toString();
	data['userName'] = entity.userName;
	return data;
}