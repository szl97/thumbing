import 'package:thumbing/data/model/personal/input/create_personal_input_entity.dart';

createPersonalInputEntityFromJson(CreatePersonalInputEntity data, Map<String, dynamic> json) {
	if(json['birthDate'] != null){
		data.birthDate = DateTime.tryParse(json['birthDate']);
	}
	if (json['constellation'] != null) {
		data.constellation = json['constellation']?.toString();
	}
	if (json['currentCountry'] != null) {
		data.currentCountry = json['currentCountry']?.toString();
	}
	if (json['gender'] != null) {
		data.gender = json['gender']?.toString();
	}
	if (json['interests'] != null) {
		data.interests = new List<CreatePersonalInputInterest>();
		(json['interests'] as List).forEach((v) {
			data.interests.add(new CreatePersonalInputInterest().fromJson(v));
		});
	}
	if (json['job'] != null) {
		data.job = new CreatePersonalInputJob().fromJson(json['job']);
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	if (json['nativeCountry'] != null) {
		data.nativeCountry = json['nativeCountry']?.toString();
	}
	if (json['nickName'] != null) {
		data.nickName = json['nickName']?.toString();
	}
	if (json['occupation'] != null) {
		data.occupation = new CreatePersonalInputOccupation().fromJson(json['occupation']);
	}
	if (json['student'] != null) {
		data.student = json['student'];
	}
	return data;
}

Map<String, dynamic> createPersonalInputEntityToJson(CreatePersonalInputEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['birthDate'] = entity.birthDate?.toString();
	data['constellation'] = entity.constellation;
	data['currentCountry'] = entity.currentCountry;
	data['gender'] = entity.gender;
	if (entity.interests != null) {
		data['interests'] =  entity.interests.map((v) => v.toJson()).toList();
	}
	if (entity.job != null) {
		data['job'] = entity.job.toJson();
	}
	data['name'] = entity.name;
	data['nativeCountry'] = entity.nativeCountry;
	data['nickName'] = entity.nickName;
	if (entity.occupation != null) {
		data['occupation'] = entity.occupation.toJson();
	}
	data['student'] = entity.student;
	return data;
}

createPersonalInputInterestFromJson(CreatePersonalInputInterest data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> createPersonalInputInterestToJson(CreatePersonalInputInterest entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

createPersonalInputJobFromJson(CreatePersonalInputJob data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> createPersonalInputJobToJson(CreatePersonalInputJob entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

createPersonalInputOccupationFromJson(CreatePersonalInputOccupation data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> createPersonalInputOccupationToJson(CreatePersonalInputOccupation entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}