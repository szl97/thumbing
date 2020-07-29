import 'dart:async';
import 'package:thumbing/data/model/content/moments.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';

class MomentsProvider {
  Future<List<Moments>> getMoments() async {
    var m = Moments();
    List<Moments> list = List.generate(10, (index) => m);
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => list,
    );
  }

  Future<MomentsDetail> getMomentsDetail(String id) async {
    return Future.delayed(
      const Duration(milliseconds: 300),
      () => MomentsDetail.getMomentsDetail(),
    );
  }
}
