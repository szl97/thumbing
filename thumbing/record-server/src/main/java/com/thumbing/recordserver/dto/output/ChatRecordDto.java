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
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:02
 */
@Data
public class ChatRecordDto implements Serializable {
    @ApiModelProperty(value = "消息Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long dataId;
    @ApiModelProperty(value = "发送方Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long fromId;
    @ApiModelProperty(value = "发送方用户名")
    private String fromUserName;
    @ApiModelProperty(value = "接受方用户名")
    private String toUserName;
    @ApiModelProperty(value = "发送方昵称")
    private String fromNickName;
    @ApiModelProperty(value = "接受方昵称")
    private String toNickName;
    @ApiModelProperty(value = "接受方Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long toId;
    @ApiModelProperty(value = "消息内容")
    private String content;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long userId1;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long userId2;
    @ApiModelProperty(value = "消息发送时间")
    private LocalDateTime time;

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj instanceof ChatRecordDto){
            ChatRecordDto recordDto = (ChatRecordDto) obj;
            return dataId.equals(recordDto.getDataId());
        }
        return false;
    }
}
