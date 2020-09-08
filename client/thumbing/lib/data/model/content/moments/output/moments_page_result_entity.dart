import 'package:equatable/equatable.dart';
import 'package:thumbing/generated/json/base/json_convert_content.dart';

class MomentsPageResultEntity with JsonConvert<MomentsPageResultEntity> {
	MomentsPageResultEntity({this.items,this.position});
	List<MomentsPageResultItems> items;
	int position;
	String totalCount;
}

class MomentsPageResultItems extends Equatable with JsonConvert<MomentsPageResultItems> {
	MomentsPageResultItems({this.commentsNum, this.content, this.createTime, this.id, this.tagIds, this.isThumb, this.thumbingNum, this.title, this.userId});
	int commentsNum;
	String content;
	DateTime createTime;
	String id;
	List<String> tagIds;
	bool isThumb;
	int thumbingNum;
	String title;
	String userId;

  @override
  // TODO: implement props
  List<Object> get props => [commentsNum, content, createTime, id, tagIds, isThumb, thumbingNum, title, userId];
}
