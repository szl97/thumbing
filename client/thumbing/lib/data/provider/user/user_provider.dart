import 'dart:async';
import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:thumbing/data/model/reponse/base_result_entity.dart';
import 'package:thumbing/data/model/user/check_auth_input_entity.dart';
import 'package:thumbing/data/model/user/login_input_entity.dart';
import 'package:thumbing/data/model/user/user.dart';
import 'package:thumbing/http/dio_manager.dart';
import 'package:thumbing/http/path/path.dart';
import 'package:thumbing/data/local/save_util.dart';

class UserProvider {
  Future<User> getUserInfo() async {
    return Future.delayed(
        const Duration(microseconds: 300), () => User.getUser());
  }

  Future<String> checkUser(String userName, String password) async {
    print("login");
    LoginInputEntity entity = new LoginInputEntity(userName: userName,password: password);
    String token;
    try{
      print(jsonEncode(entity.toJson()));
      BaseResultEntity data = await DioManager().post(HttpPath.logIn, entity.toJson());
      token = data.data;
      print(data.data);
    }on DioError catch(e){
      print(e.message);
      throw e;
    }
    if(token != null){
      DioManager.setAuthorizationHeader(token);
      await SaveUtil.saveByKey("token", token);
    }
    return token;
  }

  Future<bool> checkAuthorization(String token, String userName) async {
    CheckAuthInputEntity entity = CheckAuthInputEntity(userName: userName);
    DioManager.setAuthorizationHeader(token);
    bool b;
    try{
      BaseResultEntity data = await DioManager().get(HttpPath.checkAuthorization, entity.toJson());
      b = data.data??false;
    }on DioError catch(e){
      print(e.message);
      throw e;
    }
    if(b){
      await SaveUtil.saveByKey("token", token);
    }
    return b;
  }

  Future<void> logout(String userName) async {
    return;
  }
}
