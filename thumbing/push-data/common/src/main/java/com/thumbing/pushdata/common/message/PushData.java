package com.thumbing.pushdata.common.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.utils.serializer.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 16:52
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushData extends NodeMessage<PushData> {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long dataId;
    @JsonSerialize(using = LongToStringListSerializer.class)
    @JsonDeserialize(using = StringToLongListDeserializer.class)
    private List<Long> toUserIds;
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long fromUserId;
    private String fromUserName;
    private String fromUserNickName;
    private String data;
    private LocalDateTime time;
    @JsonSerialize(using = PushTypeSerializer.class)
    @JsonDeserialize(using = PushTypeDeserializer.class)
    private PushDataTypeEnum pushType;

    @Override
    protected Type type() {
        return Type.PD;
    }

    @Override
    protected PushData getThis(){
        return this;
    }

}
