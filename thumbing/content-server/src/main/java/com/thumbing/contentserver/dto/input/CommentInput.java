package com.thumbing.contentserver.dto.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Stan Sai
 * @date 2020-08-26 5:03
 */
@Data
public class CommentInput extends FetchCommentInput {
    @ApiModelProperty(value = "父评论Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long parentCommentId;
    @ApiModelProperty(value = "接收方Id")
    @NotNull(message = "接收方Id不可为空")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long toUserId;
    @ApiModelProperty(value = "发表方昵称")
    private String fromNickName;
    @ApiModelProperty(value = "接收方昵称")
    private String toNickName;
    @ApiModelProperty(value = "评论内容")
    @NotNull(message = "评论内容不可为空")
    @Size(max = 200, message = "评论长度限制1-200")
    private String content;
}
