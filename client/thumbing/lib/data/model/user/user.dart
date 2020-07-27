import 'package:equatable/equatable.dart';
import 'package:uuid/uuid.dart';

class User extends Equatable {
  final String id;
  final String userName;
  final String password;
  final String token;
  final DateTime lastLogin;
  final int continueDay;

  const User(this.id, this.userName, this.password, this.token, this.lastLogin,
      this.continueDay);

  static User getUser() {
    return User(
        Uuid().v4(),
        "stan",
        "123456",
        "Bear sadawsdqdqwedhjqwedhjiquwdhiuquijdqiujdwjqwioj",
        DateTime.now().add(Duration(days: 1)),
        20);
  }

  @override
  List<Object> get props => [id];
}
