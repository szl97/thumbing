package com.loserclub.pushdata.datacenter.messages;

import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
public class PushData extends NodeMessageReq<PushData> {

    private String fromUser;

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
