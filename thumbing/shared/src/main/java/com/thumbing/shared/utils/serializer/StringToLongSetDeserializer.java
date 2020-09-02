package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 15:56
 */
public class StringToLongSetDeserializer extends JsonDeserializer<Set<Long>> {
    @Override
    public Set<Long> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String s = p.getText();
        if(p.getText().startsWith("[")) {
            Iterator<Set<Object>> iterator = p.readValuesAs(new StringToLongSetDeserializer.SetTypeReference());
            Set<Object>strs = iterator.next();
            Set<Long> r = strs.stream()
                    .map(str -> Long.parseLong(str.toString()))
                    .collect(Collectors.toSet());
            return r;
        }
        else{
            Set<Long> r = new HashSet<>();
            r.add(Long.parseLong(p.getText()));
            return r;
        }
    }
    private static class SetTypeReference extends TypeReference<Set<Object>> {

    }
}
