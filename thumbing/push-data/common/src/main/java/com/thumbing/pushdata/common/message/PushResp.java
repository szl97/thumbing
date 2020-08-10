package com.thumbing.pushdata.common.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushResp extends NodeMessage<PushResp> {

    private List<Long> userIds;

    private String nodeIpWithPort;

    @Override
    protected DefinedMessage.Type type() {
        return DefinedMessage.Type.PR;
    }

    @Override
    protected PushResp getThis() {
        return this;
    }
}
