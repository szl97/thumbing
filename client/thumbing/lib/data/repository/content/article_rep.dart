import 'dart:async';
import 'package:thumbing/data/model/content/article/output/article_page_result_entity.dart';
import 'package:thumbing/data/provider/content/article_provider.dart';

class ArticleRepository {
  ArticleProvider articleProvider;
  ArticleRepository() {
    articleProvider = ArticleProvider();
  }

  Future<ArticlePageResultEntity> getArticles(int pageNum, int position) async {
    return await articleProvider.getArticles(pageNum, position);
  }
}
