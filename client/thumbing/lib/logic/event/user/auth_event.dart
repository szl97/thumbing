import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/user/user.dart';

abstract class AuthEvent extends Equatable {
  const AuthEvent();
  @override
  List<Object> get props => [];
}

class FinishLogin extends AuthEvent {
  final User user;
  const FinishLogin({this.user}) : super();
  @override
  List<Object> get props => [user];
}

class FinishLogout extends AuthEvent {}
