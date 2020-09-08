import 'package:equatable/equatable.dart';
import 'package:thumbing/generated/json/base/json_convert_content.dart';

class RoastPageResultEntity with JsonConvert<RoastPageResultEntity> {
	RoastPageResultEntity({this.items,this.position,this.totalCount});
	List<RoastPageResultItem> items;
	int position;
	String totalCount;
}

class RoastPageResultItem extends Equatable with JsonConvert<RoastPageResultItem> {
	RoastPageResultItem({this.content, this.createTime, this.id, this.isThumb, this.thumbingNum, this.userId});
	String content;
	DateTime createTime;
	String id;
	bool isThumb;
	int thumbingNum;
	String userId;

  @override
  // TODO: implement props
  List<Object> get props => [createTime,content,id,thumbingNum,isThumb,userId];
}
