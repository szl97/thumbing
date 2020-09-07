import 'dart:async';

import 'package:thumbing/data/model/roast/output/roast_page_result_entity.dart';
import 'package:thumbing/data/provider/roast/roast_provider.dart';

class RoastRepository {
  RoastProvider roastProvider;
  RoastRepository() {
    roastProvider = RoastProvider();
  }

  Future<List<RoastPageResultItem>> getRoasts() async {
    return await roastProvider.getRoasts();
  }
}
