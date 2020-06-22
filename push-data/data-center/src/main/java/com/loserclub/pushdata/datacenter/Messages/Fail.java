package com.loserclub.pushdata.datacenter.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
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
