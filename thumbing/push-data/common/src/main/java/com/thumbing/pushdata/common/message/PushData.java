package com.thumbing.pushdata.common.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.pushdata.common.constants.PushDataTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
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
    @JsonSerialize(using = pushTypeSerializer.class)
    @JsonDeserialize(using = pushTypeDeserializer.class)
    private PushDataTypeEnum pushType;

    @Override
    protected Type type() {
        return Type.PD;
    }

    @Override
    protected PushData getThis(){
        return this;
    }


    private static class pushTypeSerializer extends JsonSerializer<PushDataTypeEnum> {

        @Override
        public void serialize(PushDataTypeEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getType());
        }
    }

    private static class pushTypeDeserializer extends JsonDeserializer<PushDataTypeEnum> {

        @Override
        public PushDataTypeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String t = p.getText();
            if(t.equals(PushDataTypeEnum.RA.getType())) return PushDataTypeEnum.RA;
            if(t.equals(PushDataTypeEnum.CP.getType())) return PushDataTypeEnum.CP;
            return null;
        }
    }
}
