import 'package:equatable/equatable.dart';

class NickUser extends Equatable {
  final String userId;
  final String nickName;
  NickUser({this.userId, this.nickName});
  @override
  List<Object> get props => [userId, nickName];
}
