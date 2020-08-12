package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:14
 */
public class LongToStringListSerializer extends JsonSerializer<List<Long>> {

    private void serializeContents(List<Long> value, JsonGenerator g, SerializerProvider provider) throws IOException {
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

    public boolean hasSingleElement(List<Long> value) {
        return value.size() == 1;
    }

    @Override
    public void serialize(List<Long> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (hasSingleElement(value)) {
            serializeContents(value, gen, new DefaultSerializerProvider.Impl());
            return;
        }
        gen.writeStartArray(value);
        serializeContents(value, gen, new DefaultSerializerProvider.Impl());
        gen.writeEndArray();
    }
}
