import 'dart:async';
import 'package:thumbing/data/model/content/article.dart';

class ArticleProvider {
  Future<List<Article>> getArticles() async {
    var m = Article.getArticle();
    List<Article> list = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => list,
    );
  }
}
