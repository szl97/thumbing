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
    /**
     * Id
     */
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long targetUserId;
    /**
     * 消息内容
     */
    private String lastMessage;
    /**
     * 接收方是否已读
     */
    private int noReadNum;
    private LocalDateTime time;
}
