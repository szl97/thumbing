import 'package:thumbing/generated/json/base/json_convert_content.dart';

class MomentsPageResultEntity with JsonConvert<MomentsPageResultEntity> {
	List<MomantsPageResultItems> items;
	int position;
	String totalCount;
}

class MomantsPageResultItems with JsonConvert<MomantsPageResultItems> {
	int commentsNum;
	String content;
	DateTime createTime;
	String id;
	List<String> tagIds;
	List<String> thumbUserIds;
	int thumbingNum;
	String title;
	String userId;
}
