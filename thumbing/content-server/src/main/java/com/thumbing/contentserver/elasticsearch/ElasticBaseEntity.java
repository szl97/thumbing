package com.thumbing.contentserver.elasticsearch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-07-03
 */
@Data
@NoArgsConstructor
public class ElasticBaseEntity {
    private final String index = "doc";
    private Long id;
    private Long userId;
    private Name name;
    private String tags;
    private LocalDateTime dateTime;
    private String nickName;

    @JsonSerialize(using = NameSerializer.class)
    @JsonDeserialize(using = NameDeserializer.class)
    protected enum Name {
        ARTICLE("article"),
        MOMENTS("moments");
        private String value;

        Name(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    private static class NameSerializer extends JsonSerializer<Name> {

        @Override
        public void serialize(Name value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.value);
        }
    }

    private static class NameDeserializer extends JsonDeserializer<Name> {

        @Override
        public Name deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            String name = node.get("name").asText();
            if (name.equals(Name.ARTICLE.value)) {
                return Name.ARTICLE;
            } else if (name.equals(Name.MOMENTS.value())) {
                return Name.MOMENTS;
            }
            return null;
        }
    }
}
