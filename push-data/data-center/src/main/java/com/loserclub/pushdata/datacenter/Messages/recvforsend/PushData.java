package com.loserclub.pushdata.datacenter.messages.recvforsend;

import com.loserclub.pushdata.datacenter.messages.NodeMessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class PushData extends NodeMessageReq<PushData> {

    @Override
    protected Type type() {
        return Type.PD;
    }

    @Override
    protected PushData getThis() throws Exception {
        return this;
    }
}
