package com.thumbing.recordserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:02
 */
@Data
public class ChatRecordDto extends DocumentDto {
    /**
     * 发送方Id
     */
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long fromId;
    /**
     * 用户Id
     */
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long toId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否撤回
     */
    private boolean cancel;
    /**
     * 接收方是否已读
     */
    private boolean read;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long userId1;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long userId2;
    private LocalDateTime time;

    @Override
    public boolean equals(Object obj){
        if(obj instanceof ChatRecordDto){
            ChatRecordDto recordDto = (ChatRecordDto) obj;
            return userId1.equals(recordDto.getUserId1())
                    && userId2.equals(recordDto.getUserId2())
                    && time.equals(recordDto.getTime());
        }
        return false;
    }
}
