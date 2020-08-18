package com.thumbing.recordserver.dto.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.PushTypeDeserializer;
import com.thumbing.shared.utils.serializer.PushTypeSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 9:48
 */
@Data
public class PushDataInputDto implements Serializable {
    @ApiModelProperty(value = "消息Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @NotNull(message = "消息Id不可为空")
    private String dataId;
    @ApiModelProperty(value = "接受方Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @NotNull(message = "接受方Id不可为空")
    private Long toUserId;
    @ApiModelProperty(value = "发送方Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @NotNull(message = "发送方Id不可为空")
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
    @NotNull(message = "推送类型不可为空")
    private PushDataTypeEnum pushType;
    @ApiModelProperty(value = "是否已读")
    private boolean read;
    @ApiModelProperty(value = "消息推送时间")
    @NotNull(message = "消息推送时间不可为空")
    private LocalDateTime time;
}
