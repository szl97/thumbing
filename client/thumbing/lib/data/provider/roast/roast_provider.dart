import 'dart:async';
import 'package:thumbing/data/model/roast/roast.dart';

class RoastProvider {
  Future<List<Roast>> getRoasts() async {
    var m = Roast.getRoast();
    List<Roast> list = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => list,
    );
  }
}
