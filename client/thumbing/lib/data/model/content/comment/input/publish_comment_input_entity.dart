import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PublishCommentInputEntity with JsonConvert<PublishCommentInputEntity> {
	String content;
	String contentId;
	String contentType;
	String fromNickName;
	String parentCommentId;
	String toNickName;
	String toUserId;
}
