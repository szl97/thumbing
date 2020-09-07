import 'dart:async';
import 'package:dio/dio.dart';
import 'package:thumbing/data/model/reponse/base_result_entity.dart';
import 'package:thumbing/data/model/roast/output/roast_page_result_entity.dart';
import 'package:thumbing/data/model/roast/roast.dart';
import 'package:thumbing/generated/json/base/json_convert_content.dart';
import 'package:thumbing/http/path/path.dart';
import 'package:thumbing/http/dio_manager.dart';

class RoastProvider {
  Future<List<RoastPageResultItem>> getRoasts() async {
    var m = Roast.getRoast();
    List<RoastPageResultItem> list1 = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => list1,
    );
    List<RoastPageResultItem> list;
    try{
      BaseResultEntity data = await DioManager().get(HttpPath.fetchRoasts);
      list = data.data??JsonConvert.fromJsonAsT<List<RoastPageResultItem>>(data.data);
    } on DioError catch(e){
      print(e.message);
      throw e;
    }
    return list;
  }
}
