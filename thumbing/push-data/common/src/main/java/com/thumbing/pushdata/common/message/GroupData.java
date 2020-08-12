package com.thumbing.pushdata.common.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringListSerializer;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import com.thumbing.shared.utils.serializer.StringToLongListDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupData extends NodeMessage<GroupData> {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long fromUser;

    private String fromUserNickName;
    @JsonSerialize(using = LongToStringListSerializer.class)
    @JsonDeserialize(using = StringToLongListDeserializer.class)
    private List<Long> toUsers;

    private String name;

    private String data;

    private String sessionId;

    private LocalDateTime time;

    private boolean last;

    @Override
    protected DefinedMessage.Type type() {
        return DefinedMessage.Type.GD;
    }

    @Override
    protected GroupData getThis() {
        return this;
    }
}
