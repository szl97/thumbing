package com.thumbing.recordserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-08-15 18:44
 */
@Data
public class SessionRecordDto extends DocumentDto {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long targetUserId;
    /**
     * 消息内容
     */
    private String lastMessage;
    /**
     * 接收方未读数
     */
    private int noReadNum;
    /**
     * 最后一条消息的发送时间
     */
    private LocalDateTime time;
    /**
     * 最后阅读时间
     */
    private LocalDateTime lastReadTime;
}
