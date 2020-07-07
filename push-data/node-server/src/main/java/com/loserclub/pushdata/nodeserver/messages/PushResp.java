package com.loserclub.pushdata.nodeserver.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushResp extends NodeMessageResp<PushResp> {

    private List<Long> deviceIds;

    private String nodeIpWithPort;

    @Override
    protected Type type() {
        return Type.PR;
    }

    @Override
    protected PushResp getThis() {
        return this;
    }
}
