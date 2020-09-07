import 'dart:async';

import 'package:thumbing/data/model/content/moments/output/moments_page_result_entity.dart';
import 'package:thumbing/data/model/content/moments_detail.dart';
import 'package:thumbing/data/provider/content/moments_provider.dart';

class MomentsRepository {
  MomentsProvider momentsProvider;
  MomentsRepository() {
    momentsProvider = MomentsProvider();
  }

  Future<MomentsPageResultEntity> getMoments(int pageNum, int position) async {
    return await momentsProvider.getMoments(pageNum, position);
  }

  Future<MomentsDetail> getMomentsDetail(String id) async {
    return await momentsProvider.getMomentsDetail(id);
  }
}
