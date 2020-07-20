package com.loserclub.pushdata.common.message;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@NoArgsConstructor
public class Pong extends NodeMessage<Pong> {
    @Override
    protected Type type() {
        return Type.PO;
    }

    @Override
    protected Pong getThis() throws Exception {
        return this;
    }
}
