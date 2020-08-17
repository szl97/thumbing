package com.thumbing.shared.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:25
 */
@Data
public class ChatDataMsg implements Serializable {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "消息Id")
    private Long dataId;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "发送方id")
    private Long fromUser;
    @ApiModelProperty(value = "发送方用户名")
    private String fromUserName;
    @ApiModelProperty(value = "发送方昵称")
    private String fromUserNickName;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "接受方Id")
    private Long toUser;
    @ApiModelProperty(value = "接受方用户名")
    private String toUserName;
    @ApiModelProperty(value = "接受方昵称")
    private String toUserNickName;
    @ApiModelProperty(value = "消息内容")
    private String data;
    @ApiModelProperty(value = "消息发送时间")
    private LocalDateTime time;
}
