package com.loserclub.pushdata.datacenter.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@AllArgsConstructor
public class Pong extends NodeMessageResp<Pong> {
    @Override
    protected Type type() {
        return Type.PO;
    }

    @Override
    protected Pong getThis() throws Exception {
        return this;
    }
}