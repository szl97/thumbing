package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:51
 */
public class StringToLongListDeserializer extends JsonDeserializer<List<Long>> {
    @Override
    public List<Long> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String s = p.getText();
        if(p.getText().startsWith("[")) {
            Iterator<List<Object>> iterator = p.readValuesAs(new ListTypeReference());
            List<Object>strs = iterator.next();
            List<Long> r = strs.stream()
                    .map(str -> Long.parseLong(str.toString()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            return r;
        }
        else{
            List<Long> r = new ArrayList<>();
            r.add(Long.parseLong(p.getText()));
            return r;
        }
    }
    private static class ListTypeReference extends TypeReference<List<Object>> {

    }
}