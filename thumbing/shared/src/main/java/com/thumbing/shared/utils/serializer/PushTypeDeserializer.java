package com.thumbing.shared.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.thumbing.shared.message.PushDataTypeEnum;

import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/11 15:35
 */
public class PushTypeDeserializer extends JsonDeserializer<PushDataTypeEnum> {

    @Override
    public PushDataTypeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String t = p.getText();
        if(t.equals(PushDataTypeEnum.RA.getType())) return PushDataTypeEnum.RA;
        if(t.equals(PushDataTypeEnum.AC.getType())) return PushDataTypeEnum.AC;
        if(t.equals(PushDataTypeEnum.AT.getType())) return PushDataTypeEnum.AT;
        if(t.equals(PushDataTypeEnum.MC.getType())) return PushDataTypeEnum.MC;
        if(t.equals(PushDataTypeEnum.MT.getType())) return PushDataTypeEnum.MT;
        if(t.equals(PushDataTypeEnum.RT.getType())) return PushDataTypeEnum.RT;
        return null;
    }
}