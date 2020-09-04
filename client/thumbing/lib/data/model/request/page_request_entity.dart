import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PageRequestEntity with JsonConvert<PageRequestEntity> {
	int pageNumber;
	int pageSize;
	int position;
}
