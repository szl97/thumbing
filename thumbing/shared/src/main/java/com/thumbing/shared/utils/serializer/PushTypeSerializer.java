package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thumbing.shared.message.PushDataTypeEnum;

import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/11 15:32
 */
public class PushTypeSerializer extends JsonSerializer<PushDataTypeEnum> {

    @Override
    public void serialize(PushDataTypeEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getType());
    }
}