package com.loserclub.pushdata.nodeserver.messages;

import com.alibaba.fastjson.JSON;
import com.loserclub.pushdata.common.message.DefinedMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
public abstract class NodeMessage<T> extends DefinedMessage<T> {

    /**
     * 节点消息转换
     *
     * @return
     * @throws Exception
     */
    protected String toEncode() throws Exception {
        return JSON.toJSONString(getThis());
    }


    /**
     * 消息编码
     *
     * @return
     */
    public String encode() throws Exception {
        try {

            Message message = Message
                    .builder()
                    .type(type())
                    .message(toEncode())
                    .build();
            return JSON.toJSONString(message);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 消息解码
     *
     * @param json
     * @return
     * @throws
     */
    public static NodeMessage decode(String json) throws Exception {
        try {

            Message msg = JSON.parseObject(json, Message.class);

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
                case P:
                    cls = PushReq.class;
                    break;
                case PR:
                    cls = PushResp.class;
                    break;
                case PD:
                    cls = PushData.class;
                    break;
                default:
                    throw new Exception();
            }
            NodeMessage message = (NodeMessage) JSON.parseObject(msg.message, cls);
            return message;
        } catch (Exception e) {
            throw e;
        }
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
