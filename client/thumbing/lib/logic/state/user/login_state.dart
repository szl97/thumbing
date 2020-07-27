import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/user/user.dart';

abstract class LoginState extends Equatable {
  const LoginState();

  @override
  List<Object> get props => [];
}

class LoginInitial extends LoginState {
  final String userName;
  final String password;
  final int times;
  const LoginInitial({this.userName, this.password, this.times}) : super();

  LoginInitial copyWith({String userName, String password}) {
    return LoginInitial(
        userName: userName ?? this.userName,
        password: password ?? this.password,
        times: this.times);
  }

  bool isUserNameInValid() {
    return false;
  }

  bool isPasswordInValid() {
    return false;
  }

  @override
  List<Object> get props => [userName, password, times];
}

class SubmissionInProgress extends LoginState {}

class LoginFailure extends LoginState {
  final String userName;
  final String password;
  final String message;
  final int times;
  const LoginFailure({this.userName, this.password, this.message, this.times})
      : super();
  @override
  List<Object> get props => [userName, password, message, times];
}

class LoginSuccess extends LoginState {
  final User userInfo;

  const LoginSuccess({this.userInfo}) : super();

  @override
  List<Object> get props => [userInfo];
}

class LogoutSuccess extends LoginState {}
