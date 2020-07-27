import 'dart:async';
import 'package:thumbing/data/model/user/user.dart';
import 'package:thumbing/data/provider/user/user_provider.dart';

class UserRepository {
  UserProvider userProvider;
  UserRepository() {
    userProvider = UserProvider();
  }
  Future<User> getUserInfo() async {
    return await userProvider.getUserInfo();
  }

  Future<User> checkUser(String userName, String password) async {
    return await userProvider.checkUser(userName, password);
  }

  Future<void> logout(String userName) async {
    await userProvider.logout(userName);
  }
}
