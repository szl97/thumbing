import 'package:equatable/equatable.dart';
import 'package:thumbing/data/model/content/article.dart';
import 'package:thumbing/data/model/content/moments.dart';

class AllContent extends Equatable {
  final List<Moments> moments;
  final List<Article> articles;
  AllContent(this.moments, this.articles);

  static AllContent getAllContet() {
    var m = Moments();
    var a = Article.getArticle();
    var l1 = List.generate(10, (index) => m);
    var l2 = List.generate(10, (index) => a);
    return AllContent(l1, l2);
  }

  @override
  List<Object> get props => [moments, articles];
}
