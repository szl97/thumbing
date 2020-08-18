package com.thumbing.recordserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.PushTypeDeserializer;
import com.thumbing.shared.utils.serializer.PushTypeSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 9:09
 */
@Data
public class PushDataDto implements Serializable {
    @ApiModelProperty(value = "消息Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private String dataId;
    @ApiModelProperty(value = "接受方Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long toUserId;
    @ApiModelProperty(value = "发送方Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long fromUserId;
    @ApiModelProperty(value = "发送方用户名")
    private String fromUserName;
    @ApiModelProperty(value = "发送方昵称")
    private String fromUserNickName;
    @ApiModelProperty(value = "推送内容")
    private String data;
    @ApiModelProperty(value = "推送类型")
    @JsonSerialize(using = PushTypeSerializer.class)
    @JsonDeserialize(using = PushTypeDeserializer.class)
    private PushDataTypeEnum pushType;
    @ApiModelProperty(value = "是否已读")
    private boolean read;
    @ApiModelProperty(value = "消息推送时间")
    private LocalDateTime time;
}
