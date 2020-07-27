import 'dart:async';
import 'package:thumbing/data/model/user/user.dart';

class UserProvider {
  Future<User> getUserInfo() async {
    return Future.delayed(
        const Duration(microseconds: 300), () => User.getUser());
  }

  Future<User> checkUser(String userName, String password) {
    User user = User.getUser();
    if (user.userName != userName) {
      print(userName);
      print("用户名不存在");
      throw Exception("用户名不存在");
    } else if (user.password != password) {
      print(password);
      print("密码错误");
      throw Exception("密码错误");
    } else {
      print("ok");
      return Future.delayed(const Duration(microseconds: 300), () => user);
    }
  }

  Future<void> logout(String userName) async {
    return;
  }
}
