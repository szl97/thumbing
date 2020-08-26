package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 15:54
 */
public class LongToStringSetSerializer extends JsonSerializer<Set<Long>> {

    private void serializeContents(Set<Long> value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.setCurrentValue(value);
        Iterator<Long> it = value.iterator();
        if (!it.hasNext()) {
            return;
        }
        int i = 0;
        do {
            Long elem = it.next();
            if (elem == null) {
                provider.defaultSerializeNull(g);
            } else {
                JsonSerializer<Long> serializer = new LongToStringSerializer();
                serializer.serialize(elem, g, provider);
            }
            ++i;
        } while (it.hasNext());
    }

    public boolean hasSingleElement(Set<Long> value) {
        return value.size() == 1;
    }

    @Override
    public void serialize(Set<Long> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (hasSingleElement(value)) {
            serializeContents(value, gen, new DefaultSerializerProvider.Impl());
            return;
        }
        gen.writeStartArray(value);
        serializeContents(value, gen, new DefaultSerializerProvider.Impl());
        gen.writeEndArray();
    }
}
