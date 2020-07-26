import 'dart:async';
import 'package:thumbing/data/model/content/article.dart';
import 'package:thumbing/data/provider/content/article_provider.dart';

class ArticleRepository {
  ArticleProvider articleProvider;
  ArticleRepository() {
    articleProvider = ArticleProvider();
  }

  Future<List<Article>> getArticles() async {
    return await articleProvider.getArticles();
  }
}
