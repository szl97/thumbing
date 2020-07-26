import 'dart:async';
import 'package:thumbing/data/model/content/moments.dart';

class MomentsProvider {
  Future<List<Moments>> getMoments() async {
    var m = Moments();
    List<Moments> list = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => list,
    );
  }
}
