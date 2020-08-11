package com.thumbing.pushdata.common.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.utils.serializer.PushTypeDeserializer;
import com.thumbing.shared.utils.serializer.PushTypeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Long> toUserIds;
    private Long fromUserId;
    private String fromUserName;
    private String fromUserNickName;
    private String data;
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
