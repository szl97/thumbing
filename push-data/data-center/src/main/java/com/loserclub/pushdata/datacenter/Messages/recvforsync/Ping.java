package com.loserclub.pushdata.datacenter.Messages.recvforsync;

import com.loserclub.pushdata.datacenter.Messages.NodeMessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Ping extends NodeMessageReq<Ping> {
    @Override
    protected Type type() {
        return Type.PI;
    }

    @Override
    protected Ping getThis() throws Exception {
        return this;
    }
}
