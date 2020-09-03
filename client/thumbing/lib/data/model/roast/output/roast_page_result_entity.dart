import 'package:thumbing/generated/json/base/json_convert_content.dart';

class RoastPageResultEntity with JsonConvert<RoastPageResultEntity> {
	List<RoastPageResultItem> items;
	int position;
	String totalCount;
}

class RoastPageResultItem with JsonConvert<RoastPageResultItem> {
	String content;
	DateTime createTime;
	String id;
	List<String> thumbUserIds;
	int thumbingNum;
	String userId;
}
