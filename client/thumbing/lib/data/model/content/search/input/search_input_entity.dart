import 'package:thumbing/generated/json/base/json_convert_content.dart';

class SearchInputEntity with JsonConvert<SearchInputEntity> {
	int pageNumber;
	int pageSize;
	String keyword;
}
