package com.thumbing.pushdata.common.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.shared.utils.context.SpringContextUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Data
@NoArgsConstructor
public abstract class NodeMessage<T> extends DefinedMessage<T> {

    private static ObjectMapper objectMapper = SpringContextUtils.getBean(ObjectMapper.class);

    /**
     * 节点消息转换
     *
     * @return
     * @throws Exception
     */
    protected String toEncode() throws JsonProcessingException {
        return objectMapper.writeValueAsString(getThis());
    }


    /**
     * 消息编码
     *
     * @return
     */
    public String encode() throws JsonProcessingException {

            Message message = Message
                    .builder()
                    .type(type())
                    .message(toEncode())
                    .build();
            return objectMapper.writeValueAsString(message);
    }


    /**
     * 消息解码
     *
     * @param json
     * @return
     * @throws
     */
    public static NodeMessage decode(String json) throws JsonProcessingException {


        Message msg = objectMapper.readValue(json, Message.class);

        Class cls = null;

        switch (msg.type) {
            case C:
                cls = Confirm.class;
                break;
            case PI:
                cls = Ping.class;
                break;
            case PO:
                cls = Pong.class;
                break;
            case CS:
                cls = ConnectSet.class;
                break;
            case PR:
                cls = PushResp.class;
                break;
            case CD:
                cls = ChatData.class;
                break;
            case PD:
                cls = ChatData.class;
                break;
        }
        NodeMessage message = (NodeMessage) objectMapper.readValue(msg.message, cls);
        return message;

    }

    //真正的传递消息的类
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Message {
        private Type type;
        private String message;
    }
}
