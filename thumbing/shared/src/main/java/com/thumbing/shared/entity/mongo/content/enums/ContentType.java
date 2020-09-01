package com.thumbing.shared.entity.mongo.content.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 17:10
 */
@JsonSerialize(using = ContentType.ContentTypeSerializer.class)
@JsonDeserialize(using = ContentType.ContentTypeDeserializer.class)
public enum ContentType {
    ARTICLE("article"),
    MOMENTS("moments");
    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static class ContentTypeSerializer extends JsonSerializer<ContentType> {

        @Override
        public void serialize(ContentType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.value);
        }
    }

    public static class ContentTypeDeserializer extends JsonDeserializer<ContentType> {

        @Override
        public ContentType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String name = p.getText();
            if (name.equals(ContentType.ARTICLE.value)) {
                return ContentType.ARTICLE;
            } else if (name.equals(ContentType.MOMENTS.value())) {
                return ContentType.MOMENTS;
            }
            return null;
        }
    }
}
