import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class User extends Equatable {
  final String id;
  final String userName;
  final String password;
  final String token;
  final DateTime lastLogin;
  final int continueDay;

  const User(
      {this.id,
      this.userName,
      this.password,
      this.token,
      this.lastLogin,
      this.continueDay});

  static User getUser() {
    return User(
        id: Uuid().v4(),
        userName: "stan",
        password: "123456",
        token: "Bear sadawsdqdqwedhjqwedhjiquwdhiuquijdqiujdwjqwioj",
        lastLogin: DateTime.now().add(Duration(days: 1)),
        continueDay: 20);
  }

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
        id: json['id'],
        userName: json['userName'],
        password: json['password'],
        token: json['token'],
        lastLogin: json['lastLogin'],
        continueDay: json['continueDay']);
  }

  @override
  List<Object> get props => [id];
}
