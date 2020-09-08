import 'package:thumbing/generated/json/base/json_convert_content.dart';

class ParentCommentEntity with JsonConvert<ParentCommentEntity> {
	List<ParentCommentChildComments> childComments;
	String commentId;
	String content;
	String contentId;
	String contentType;
	DateTime createTime;
	String fromNickName;
	String fromUserId;
	String id;
	bool isThumb;
	int thumbingNum;
}

class ParentCommentChildComments with JsonConvert<ParentCommentChildComments> {
	String commentId;
	String content;
	String createTime;
	String fromNickName;
	String fromUserId;
	String id;
	String parentCommentId;
	bool isThumb;
	int thumbingNum;
	String toNickName;
	String toUserId;
}
