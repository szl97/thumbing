package com.loserclub.pushdata.common.constants;

import io.netty.util.AttributeKey;
import lombok.Getter;

@Getter
public enum AttributeEnum {
    /*****
     *
     * Device 和 NodeServer 之间
     *
     * ***/
    CHANNEL_ATTR_DEVICE (AttributeKey.newInstance("device")),
    //长连接状态
    CHANNEL_ATTR_HANDSHAKE(AttributeKey.newInstance("handshake")),

    /*****
     *
     * NodeServer 与 DataCenter 之间
     *
     * ***/
     CHANNEL_ATTR_DATACENTER(AttributeKey.newInstance("datacenter"));


    private  AttributeKey attributeKey;

    AttributeEnum(AttributeKey attributeKey){
        this.attributeKey = attributeKey;
    }
}
