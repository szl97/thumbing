package com.thumbing.recordserver.dto.output;

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
 * @date 2020-08-15 18:44
 */
@Data
public class SessionRecordDto implements Serializable {
    @ApiModelProperty(value = "对方用户Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long targetUserId;
    @ApiModelProperty(value = "对方用户昵称")
    private String targetNickName;
    @ApiModelProperty(value = "最后一条消息内容")
    private String lastMessage;
    @ApiModelProperty(value = "最后一条消息Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long lastDataId;
    @ApiModelProperty(value = "未读消息数")
    private int noReadNum;
    @ApiModelProperty(value = "最后一条消息的发送时间")
    private LocalDateTime time;
    @ApiModelProperty(value = "最后阅读时间")
    private LocalDateTime lastReadTime;
}
