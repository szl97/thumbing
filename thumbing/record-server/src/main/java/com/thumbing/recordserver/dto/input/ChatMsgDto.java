package com.thumbing.recordserver.dto.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 8:51
 */
@Data
public class ChatMsgDto implements Serializable {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "消息Id")
    @NotNull(message = "消息Id不可为空")
    private Long dataId;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "发送方id")
    @NotNull(message = "发送方Id不可为空")
    private Long fromUser;
    @ApiModelProperty(value = "发送方用户名")
    private String fromUserName;
    @ApiModelProperty(value = "发送方昵称")
    private String fromUserNickName;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "接受方Id")
    @NotNull(message = "接受方Id不可为空")
    private Long toUser;
    @ApiModelProperty(value = "接受方用户名")
    private String toUserName;
    @ApiModelProperty(value = "接受方昵称")
    private String toUserNickName;
    @ApiModelProperty(value = "消息内容")
    private String data;
    @ApiModelProperty(value = "消息发送时间")
    @NotNull(message = "消息发送时间不可为空")
    private LocalDateTime time;
}
