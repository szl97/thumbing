import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PersonalConfigurationEntity with JsonConvert<PersonalConfigurationEntity> {
	List<PersonalConfigurationInterest> interests;
	List<PersonalConfigurationJob> jobs;
	List<PersonalConfigurationOccupation> occupations;
}

class PersonalConfigurationInterest with JsonConvert<PersonalConfigurationInterest> {
	String id;
	String name;
}

class PersonalConfigurationJob with JsonConvert<PersonalConfigurationJob> {
	String id;
	String name;
}

class PersonalConfigurationOccupation with JsonConvert<PersonalConfigurationOccupation> {
	String id;
	String name;
}
