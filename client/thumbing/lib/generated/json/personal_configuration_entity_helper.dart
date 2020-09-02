import 'package:thumbing/data/model/personal/output/personal_configuration_entity.dart';

personalConfigurationEntityFromJson(PersonalConfigurationEntity data, Map<String, dynamic> json) {
	if (json['interests'] != null) {
		data.interests = new List<PersonalConfigurationInterest>();
		(json['interests'] as List).forEach((v) {
			data.interests.add(new PersonalConfigurationInterest().fromJson(v));
		});
	}
	if (json['jobs'] != null) {
		data.jobs = new List<PersonalConfigurationJob>();
		(json['jobs'] as List).forEach((v) {
			data.jobs.add(new PersonalConfigurationJob().fromJson(v));
		});
	}
	if (json['occupations'] != null) {
		data.occupations = new List<PersonalConfigurationOccupation>();
		(json['occupations'] as List).forEach((v) {
			data.occupations.add(new PersonalConfigurationOccupation().fromJson(v));
		});
	}
	return data;
}

Map<String, dynamic> personalConfigurationEntityToJson(PersonalConfigurationEntity entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	if (entity.interests != null) {
		data['interests'] =  entity.interests.map((v) => v.toJson()).toList();
	}
	if (entity.jobs != null) {
		data['jobs'] =  entity.jobs.map((v) => v.toJson()).toList();
	}
	if (entity.occupations != null) {
		data['occupations'] =  entity.occupations.map((v) => v.toJson()).toList();
	}
	return data;
}

personalConfigurationInterestFromJson(PersonalConfigurationInterest data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> personalConfigurationInterestToJson(PersonalConfigurationInterest entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

personalConfigurationJobFromJson(PersonalConfigurationJob data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> personalConfigurationJobToJson(PersonalConfigurationJob entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}

personalConfigurationOccupationFromJson(PersonalConfigurationOccupation data, Map<String, dynamic> json) {
	if (json['id'] != null) {
		data.id = json['id']?.toString();
	}
	if (json['name'] != null) {
		data.name = json['name']?.toString();
	}
	return data;
}

Map<String, dynamic> personalConfigurationOccupationToJson(PersonalConfigurationOccupation entity) {
	final Map<String, dynamic> data = new Map<String, dynamic>();
	data['id'] = entity.id;
	data['name'] = entity.name;
	return data;
}