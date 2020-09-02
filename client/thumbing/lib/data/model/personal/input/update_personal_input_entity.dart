import 'package:thumbing/generated/json/base/json_convert_content.dart';

class UpdatePersonalInputEntity with JsonConvert<UpdatePersonalInputEntity> {
	String constellation;
	String currentCountry;
	String id;
	List<UpdatePersonalInputInterest> interests;
	UpdatePersonalInputJob job;
	String nickName;
	UpdatePersonalInputOccupation occupation;
	bool student;
}

class UpdatePersonalInputInterest with JsonConvert<UpdatePersonalInputInterest> {
	String id;
	String name;
}

class UpdatePersonalInputJob with JsonConvert<UpdatePersonalInputJob> {
	String id;
	String name;
}

class UpdatePersonalInputOccupation with JsonConvert<UpdatePersonalInputOccupation> {
	String id;
	String name;
}
