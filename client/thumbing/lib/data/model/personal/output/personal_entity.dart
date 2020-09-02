import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PersonalEntity with JsonConvert<PersonalEntity> {
	//@JSONField(name: "birth_date")
	DateTime birthDate;
	String constellation;
	String currentCountry;
	String gender;
	String id;
	List<PersonalInterest> interests;
	PersonalJob job;
	String name;
	String nativeCountry;
	String nickName;
	PersonalOccupation occupation;
	bool student;
	String userName;
}

class PersonalInterest with JsonConvert<PersonalInterest> {
	String id;
	String name;
}

class PersonalJob with JsonConvert<PersonalJob> {
	String id;
	String name;
}

class PersonalOccupation with JsonConvert<PersonalOccupation> {
	String id;
	String name;
}
