package com.thumbing.contentserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.LongToStringSetSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import com.thumbing.shared.utils.serializer.StringToLongSetDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/19 15:29
 */
@Data
public class CommentDto extends DocumentDto {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "评论Id")
    private Long commentId;
    @ApiModelProperty(value = "文章或帖子Id")
    private String contentId;
    @ApiModelProperty(value = "内容类型")
    private ContentType contentType;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "发送方Id")
    private Long fromUserId;
    @ApiModelProperty(value = "发表方昵称")
    private String fromNickName;
    @ApiModelProperty(value = "内容")
    private String content;
    @JsonSerialize(using = LongToStringSetSerializer.class)
    @JsonDeserialize(using = StringToLongSetDeserializer.class)
    @ApiModelProperty(value = "点赞用户")
    private Set<Long> thumbUserIds;
    @ApiModelProperty(value = "点赞数")
    private Integer thumbingNum;
    /**
     * 子评论
     */
    List<ChildCommentDto> childComments;
}
