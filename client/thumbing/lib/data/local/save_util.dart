import 'package:shared_preferences/shared_preferences.dart';

class SaveUtil {
  //读取数据
  static Future<String> getSavedByKey(String keyName) async {
    print('进入到读取数据');
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    String data = await sharedPreferences.getString(keyName);
    print('进入到读取数据-返回数据');
    return data;
  }

  //保存数据
  static Future<bool> saveByKey(String keyName, String value) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    bool isOk = await sharedPreferences.setString(keyName, value);
    if (isOk)
      print('写入数据成功');
    else
      print('写入数据失败');
    return isOk;
  }

  //删除数据
  static Future<bool> removeByKey(String keyName) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    bool isOk = await sharedPreferences.remove(keyName);
    if (isOk)
      print('删除数据成功');
    else
      print('删除数据失败');
    return isOk;
  }
}
