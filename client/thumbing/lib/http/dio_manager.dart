import 'dart:html';

import 'package:dio/dio.dart';
import 'package:thumbing/data/local/save_util.dart';
import 'package:thumbing/data/model/reponse/base_result_entity.dart';

class DioManager {
  factory DioManager() =>_getInstance();
  static DioManager get instance => _getInstance();
  static DioManager _instance;
  Dio _dio;
  
  static String _baseUrl = "192.168.60.152:8081";
  DioManager._internal() {
    _dio = Dio(BaseOptions(baseUrl:_baseUrl, connectTimeout: 15000, responseType: ResponseType.json));
  }

  static DioManager _getInstance() {
    if (_instance == null) {
      _instance = new DioManager._internal();
    }
    return _instance;
  }
  
  static void setAuthorizationHeader(String token){
    if(_instance == null) _instance = _getInstance();
    if(_instance._dio.options.headers == null){
      Map<String, dynamic> map = new Map();
      map.putIfAbsent("Authorization", () => token);
      _instance._dio.options.headers = map;
    }
    else{
      _instance._dio.options.headers.putIfAbsent("Authorization", () => token);
    }
  }

 get(String path, Map<String, dynamic> params) async {
    Response response;
    response = await _dio.get(path, queryParameters: params);
    String token = response.headers.value("RefreshAuthorization");
    if(token != null){
      //refresh token
      setAuthorizationHeader(token);
      SaveUtil.saveByKey("token", token);
    }
    return toBaseResult(response.data);
  }

  post(String path, Map<String, dynamic> params) async {
    Response response;
    response = await _dio.post(path, queryParameters: params);
    String token = response.headers.value("RefreshAuthorization");
    if(token != null){
      //refresh token
      setAuthorizationHeader(token);
      SaveUtil.saveByKey("token", token);
    }
    return toBaseResult(response.data);
  }

  put(String path, Map<String, dynamic> params) async {
    Response response;
    response = await _dio.put(path, queryParameters: params);
    String token = response.headers.value("RefreshAuthorization");
    if(token != null){
      //refresh token
      setAuthorizationHeader(token);
      SaveUtil.saveByKey("token", token);
    }
    return toBaseResult(response.data);
  }

  patch(String path, Map<String, dynamic> params) async {
    Response response;
    response = await _dio.patch(path, queryParameters: params);
    String token = response.headers.value("RefreshAuthorization");
    if(token != null){
      //refresh token
      setAuthorizationHeader(token);
      SaveUtil.saveByKey("token", token);
    }
    return toBaseResult(response.data);
  }

  delete(String path, Map<String, dynamic> params) async {
    Response response;
    response = await _dio.delete(path, queryParameters: params);
    String token = response.headers.value("RefreshAuthorization");
    if(token != null){
      //refresh token
      setAuthorizationHeader(token);
      SaveUtil.saveByKey("token", token);
    }
    return toBaseResult(response.data);
  }

  BaseResultEntity toBaseResult(Map<String, dynamic> map){
    return new BaseResultEntity().fromJson(map);
  }
}