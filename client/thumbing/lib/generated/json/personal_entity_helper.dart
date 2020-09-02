import 'package:thumbing/data/model/personal/output/personal_entity.dart';

personalEntityFromJson(PersonalEntity data, Map<String, dynamic> json) {
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
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['interests'] != null) {
		data.interests = new List<PersonalInterest>();
		(json['interests'] as List).forEach((v) {
			data.interests.add(new PersonalInterest().fromJson(v));
		});
	}
	if (json['job'] != null) {
		data.job = new PersonalJob().fromJson(json['job']);
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
		data.occupation = new PersonalOccupation().fromJson(json['occupation']);
	}
	if (json['student'] != null) {
		data.student = json['student'];
	}
	if (json['userName'] != null) {
		data.userName = json['userName']?.toString();
	}
	return data;
}

Map<String, dynamic> personalEntityToJson(PersonalEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['birthDate'] = entity.birthDate?.toString();
	data['constellation'] = entity.constellation;
	data['currentCountry'] = entity.currentCountry;
	data['gender'] = entity.gender;
	data['id'] = entity.id;
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
	data['userName'] = entity.userName;
	return data;
}

personalInterestFromJson(PersonalInterest data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> personalInterestToJson(PersonalInterest entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

personalJobFromJson(PersonalJob data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> personalJobToJson(PersonalJob entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

personalOccupationFromJson(PersonalOccupation data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> personalOccupationToJson(PersonalOccupation entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}