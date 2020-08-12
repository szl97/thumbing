package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:51
 */
public class StringToLongListDeserializer extends JsonDeserializer<List<Long>> {
    @Override
    public List<Long> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String s = p.getText();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, List.class);
    }
}