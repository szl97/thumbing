import 'dart:async';
import 'package:dio/dio.dart';
import 'package:thumbing/data/model/content/article.dart';
import 'package:thumbing/data/model/content/article/output/article_page_result_entity.dart';
import 'package:thumbing/data/model/reponse/base_result_entity.dart';
import 'package:thumbing/data/model/request/page_request_entity.dart';
import 'package:thumbing/http/path/path.dart';
import 'package:thumbing/http/dio_manager.dart';

class ArticleProvider {
  Future<ArticlePageResultEntity> getArticles(int pageNum, int position) async {
    var m = Article.getArticle();
    List<ArticlePageResultItem> list = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () =>ArticlePageResultEntity(items:list, position: 200),
    );
    PageRequestEntity requestEntity = PageRequestEntity(position: position, pageNumber: pageNum, pageSize: 20);
    ArticlePageResultEntity entity;
    try{
    BaseResultEntity data = await DioManager().get(HttpPath.fetchArticles, requestEntity.toJson());
      entity = data.data??entity.fromJson(data.data);
    } on DioError catch(e){
      print(e.message);
      throw e;
     }
     return entity;
  }
}
