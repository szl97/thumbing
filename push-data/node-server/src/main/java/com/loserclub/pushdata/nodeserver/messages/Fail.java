package com.loserclub.pushdata.nodeserver.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Builder
@Data
@NoArgsConstructor
public class Fail extends NodeMessageResp {
    @Override
    protected Type type() {
        return Type.F;
    }

    @Override
    protected Object getThis() throws Exception {
        return this;
    }
}
