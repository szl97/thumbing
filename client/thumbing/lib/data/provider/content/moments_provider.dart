import 'dart:async';

import 'package:dio/dio.dart';
import 'package:thumbing/data/model/content/moments.dart';
import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';
import 'package:thumbing/data/model/reponse/base_result_entity.dart';
import 'package:thumbing/data/model/request/page_request_entity.dart';
import 'package:thumbing/http/dio_manager.dart';
import 'package:thumbing/http/path/path.dart';

class MomentsProvider {
  Future<MomentsPageResultEntity> getMoments(int pageNum, int position) async {
    var m = Moments.getMoments();
    List<MomentsPageResultItems> list = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => MomentsPageResultEntity(items: list, position: 200),
    );
    PageRequestEntity requestEntity = PageRequestEntity(position: position, pageNumber: pageNum, pageSize: 20);
    MomentsPageResultEntity entity;
    try{
      BaseResultEntity data = await DioManager().get(HttpPath.fetchMoments, requestEntity.toJson());
      entity = data.data??entity.fromJson(data.data);
    }on DioError catch(e){
      print(e.message);
      throw e;
    }
    return entity;
  }

  Future<MomentsDetail> getMomentsDetail(String id) async {
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => MomentsDetail.getMomentsDetail(),
    );
  }
}
