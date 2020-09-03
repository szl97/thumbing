import 'package:thumbing/generated/json/base/json_convert_content.dart';

class ParentCommentEntity with JsonConvert<ParentCommentEntity> {
	List<ParentCommantChildCommants> childComments;
	String commentId;
	String content;
	String contentId;
	String contentType;
	DateTime createTime;
	String fromNickName;
	String fromUserId;
	String id;
	List<String> thumbUserIds;
	int thumbingNum;
}

class ParentCommantChildCommants with JsonConvert<ParentCommantChildCommants> {
	String commentId;
	String content;
	String createTime;
	String fromNickName;
	String fromUserId;
	String id;
	String parentCommentId;
	List<String> thumbUserIds;
	int thumbingNum;
	String toNickName;
	String toUserId;
}
