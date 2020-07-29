import 'dart:async';
import 'package:thumbing/data/model/content/moments.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';
import 'package:thumbing/data/provider/content/moments_provider.dart';

class MomentsRepository {
  MomentsProvider momentsProvider;
  MomentsRepository() {
    momentsProvider = MomentsProvider();
  }

  Future<List<Moments>> getMoments() async {
    return await momentsProvider.getMoments();
  }

  Future<MomentsDetail> getMomentsDetail(String id) async {
    return await momentsProvider.getMomentsDetail(id);
  }
}
