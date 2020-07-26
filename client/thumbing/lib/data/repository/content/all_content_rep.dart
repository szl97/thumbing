import 'dart:async';
import 'package:thumbing/data/model/content/all_content.dart';
import 'package:thumbing/data/provider/content/all_content_provider.dart';

class AllContentRepository {
  AllContentProvider allContentProvider;
  AllContentRepository() {
    allContentProvider = AllContentProvider();
  }

  Future<AllContent> getAllContent() async {
    return await allContentProvider.getAllContent();
  }
}
