import 'package:thumbing/generated/json/base/json_convert_content.dart';

class DeleteCommentInputEntity with JsonConvert<DeleteCommentInputEntity> {
	String id;
	String contentId;
	String contentType;
}
