package com.loserclub.pushdata.datacenter.messages;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PushData extends NodeMessageReq<PushData> {

    private List<String> deviceIds;

    private String name;

    private String data;

    @Override
    protected Type type() {
        return Type.PD;
    }

    @Override
    protected PushData getThis() throws Exception {
        return this;
    }
}
