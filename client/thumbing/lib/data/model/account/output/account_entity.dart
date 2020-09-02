import 'package:thumbing/generated/json/base/json_convert_content.dart';
import 'package:equatable/equatable.dart';

class AccountEntity extends Equatable with JsonConvert<AccountEntity> {
	bool access;
	bool active;
	int continueDays;
	String id;
	DateTime lastLogin;
	String userName;

  @override
  // TODO: implement props
  List<Object> get props => [access, active, continueDays, id, lastLogin, userName];
}
