package com.loserclub.pushdata.common.message;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.val;

import java.io.IOException;

/**
 * 定义客户端与node-server之间以及node-server与data-center之间传输的数据类型
 * @author Stan Sai
 * @date 2020-06-21
 */
public abstract class DefinedMessage<T> {
    private static class TypeSerializer extends JsonSerializer<Type> {

        @Override
        public void serialize(Type value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.type);
        }
    }

    private static class TypeDeserializer extends JsonDeserializer<Type> {

        @Override
        public Type deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String t = p.getText();
            if(t.equals(Type.C.type)) return Type.C;
            if(t.equals(Type.PI.type)) return Type.PI;
            if(t.equals(Type.PO.type)) return Type.PO;
            if(t.equals(Type.P.type)) return Type.P;
            if(t.equals(Type.PR.type)) return Type.PR;
            if(t.equals(Type.PD.type)) return Type.PD;
            if(t.equals(Type.CD.type)) return Type.CD;
            if(t.equals(Type.CS.type)) return Type.CS;
            if(t.equals(Type.HS.type)) return Type.HS;
            if(t.equals(Type.F.type)) return Type.F;
            if(t.equals(Type.IN.type)) return Type.IN;
            return null;
        }
    }
    /**r
     * 消息类型
     * 1)连接成功确认 收到Server Node的连接成功确认将Channel绑定Node Server Name并保存
     * 2)心跳请求
     * 3)心跳响应
     * 4）请求
     * 5)回应
     * 6）聊天消息
     * 7)Node Server通知ClientMonitor，建立了新的Client连接，或者关闭了与某个Client的连接
     * 8)除聊天外的其他消息推送
     * 9)长连接
     * 10)失败
     * 11）取消连接
     */
    @JsonSerialize(using = TypeSerializer.class)
    @JsonDeserialize(using = TypeDeserializer.class)
    protected enum Type {
        C("confirm"),           // CONFIRM
        PI("ping"),             // PING
        PO("pong"),             // PONG
        P("push_req"),          // PUSH_REQ
        PR("push_resp"),        // PUSH_RESP
        CD("chat_data"),        // CHAT_DATA
        CS("connection_set"),   // CONNECTION_SET
        PD("push_data"),        // PUSH_DATA
        HS("hand_shake"),       // HandShake
        F("fail"),              // FAIL
        IN("inactive");         // INACTIVE
        Type(String type){
            this.type = type;
        }
        String type;
        String getType(){
            return type;
        }
    }

    /**
     * 获取设备消息类型
     *
     * @return
     */
    protected abstract Type type();


    protected abstract T getThis() throws Exception;

}
