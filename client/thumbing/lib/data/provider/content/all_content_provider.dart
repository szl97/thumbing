import 'dart:async';
import 'package:thumbing/data/model/content/all_content.dart';

class AllContentProvider {
  Future<AllContent> getAllContent() async {
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => AllContent.getAllContet(),
    );
  }
}
