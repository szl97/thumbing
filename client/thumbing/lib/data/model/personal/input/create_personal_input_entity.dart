import 'package:thumbing/generated/json/base/json_convert_content.dart';

class CreatePersonalInputEntity with JsonConvert<CreatePersonalInputEntity> {
	DateTime birthDate;
	String constellation;
	String currentCountry;
	String gender;
	List<CreatePersonalInputInterest> interests;
	CreatePersonalInputJob job;
	String name;
	String nativeCountry;
	String nickName;
	CreatePersonalInputOccupation occupation;
	bool student;
}

class CreatePersonalInputInterest with JsonConvert<CreatePersonalInputInterest> {
	String id;
	String name;
}

class CreatePersonalInputJob with JsonConvert<CreatePersonalInputJob> {
	String id;
	String name;
}

class CreatePersonalInputOccupation with JsonConvert<CreatePersonalInputOccupation> {
	String id;
	String name;
}
