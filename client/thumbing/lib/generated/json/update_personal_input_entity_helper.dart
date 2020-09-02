import 'package:thumbing/data/model/personal/input/update_personal_input_entity.dart';

updatePersonalInputEntityFromJson(UpdatePersonalInputEntity data, Map<String, dynamic> json) {
	if (json['constellation'] != null) {
		data.constellation = json['constellation']?.toString();
	}
	if (json['currentCountry'] != null) {
		data.currentCountry = json['currentCountry']?.toString();
	}
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['interests'] != null) {
		data.interests = new List<UpdatePersonalInputInterest>();
		(json['interests'] as List).forEach((v) {
			data.interests.add(new UpdatePersonalInputInterest().fromJson(v));
		});
	}
	if (json['job'] != null) {
		data.job = new UpdatePersonalInputJob().fromJson(json['job']);
	}
	if (json['nickName'] != null) {
		data.nickName = json['nickName']?.toString();
	}
	if (json['occupation'] != null) {
		data.occupation = new UpdatePersonalInputOccupation().fromJson(json['occupation']);
	}
	if (json['student'] != null) {
		data.student = json['student'];
	}
	return data;
}

Map<String, dynamic> updatePersonalInputEntityToJson(UpdatePersonalInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['constellation'] = entity.constellation;
	data['currentCountry'] = entity.currentCountry;
	data['id'] = entity.id;
	if (entity.interests != null) {
		data['interests'] =  entity.interests.map((v) => v.toJson()).toList();
	}
	if (entity.job != null) {
		data['job'] = entity.job.toJson();
	}
	data['nickName'] = entity.nickName;
	if (entity.occupation != null) {
		data['occupation'] = entity.occupation.toJson();
	}
	data['student'] = entity.student;
	return data;
}

updatePersonalInputInterestFromJson(UpdatePersonalInputInterest data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> updatePersonalInputInterestToJson(UpdatePersonalInputInterest entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

updatePersonalInputJobFromJson(UpdatePersonalInputJob data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> updatePersonalInputJobToJson(UpdatePersonalInputJob entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

updatePersonalInputOccupationFromJson(UpdatePersonalInputOccupation data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> updatePersonalInputOccupationToJson(UpdatePersonalInputOccupation entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}