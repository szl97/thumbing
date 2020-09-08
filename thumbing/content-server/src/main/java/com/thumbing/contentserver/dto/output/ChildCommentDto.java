package com.thumbing.contentserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.LongToStringSetSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import com.thumbing.shared.utils.serializer.StringToLongSetDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/19 15:31
 */
@Data
public class ChildCommentDto extends DocumentDto {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "评论Id")
    private Long commentId;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "父评论Id")
    private Long parentCommentId;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "发送方Id")
    private Long fromUserId;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "接收方Id")
    private Long toUserId;
    @ApiModelProperty(value = "发表方昵称")
    private String fromNickName;
    @ApiModelProperty(value = "接收方昵称")
    private String toNickName;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "是否点赞")
    private boolean isThumb;
    @ApiModelProperty(value = "点赞数")
    private int thumbingNum;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
