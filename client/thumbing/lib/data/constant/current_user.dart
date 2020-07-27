import 'package:thumbing/data/model/user/user.dart';

class CurrentUser {
  static User currentUser;

  static void clear() {
    currentUser = null;
  }

  static void setUser(User user) {
    currentUser = user;
  }

  static String getHttpHeader() {
    return "Bear " + currentUser.token;
  }

  static String getUserName() {
    return currentUser.userName;
  }

  static String getPassword() {
    return currentUser.password;
  }
}
