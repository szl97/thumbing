import 'dart:async';
import 'package:thumbing/data/model/roast/roast.dart';
import 'package:thumbing/data/provider/roast/roast_provider.dart';

class RoastRepository {
  RoastProvider roastProvider;
  RoastRepository() {
    roastProvider = RoastProvider();
  }

  Future<List<Roast>> getRoasts() async {
    return await roastProvider.getRoasts();
  }
}
