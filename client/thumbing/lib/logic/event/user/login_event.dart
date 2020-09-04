import 'package:equatable/equatable.dart';

abstract class LoginEvent extends Equatable {
  const LoginEvent();
  @override
  List<Object> get props => [];
}

class InitializeLogin extends LoginEvent {}

class Login extends LoginEvent {
  final String token;
  final String userName;
  final String password;
  const Login({this.token, this.userName, this.password}) : super();
  @override
  List<Object> get props => [token, userName, password];
}

class Logout extends LoginEvent {
  final String userName;
  const Logout({this.userName}) : super();
  @override
  List<Object> get props => [userName];
}

class LoginUsernameChanged extends LoginEvent {
  final String userName;
  const LoginUsernameChanged({this.userName}) : super();
  @override
  List<Object> get props => [userName];
}

class LoginPasswordChanged extends LoginEvent {
  final String password;
  const LoginPasswordChanged({this.password}) : super();
  @override
  List<Object> get props => [password];
}
