package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 15:40
 */
public class StringToLongDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String t = p.getText();
        return Long.parseLong(t);
    }
}
