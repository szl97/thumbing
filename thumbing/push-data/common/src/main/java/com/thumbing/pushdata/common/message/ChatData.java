package com.thumbing.pushdata.common.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-08-11 20:54
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatData extends NodeMessage<ChatData> {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long dataId;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long fromUser;

    private String fromUserName;

    private String fromUserNickName;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long toUser;

    private String toUserName;

    private String toUserNickName;

    private String name;

    private String data;

    private LocalDateTime time;

    private boolean last;
    @Override
    protected Type type() {
        return Type.CD;
    }

    @Override
    protected ChatData getThis() {
        return this;
    }
}
