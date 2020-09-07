import 'package:thumbing/generated/json/base/json_convert_content.dart';
import 'package:equatable/equatable.dart';

class LoginInputEntity extends Equatable with JsonConvert<LoginInputEntity> {
	LoginInputEntity({this.userName, this.password});
	String userName;
	String password;

  @override
  // TODO: implement props
  List<Object> get props => [userName,password];
}
