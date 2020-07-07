package com.loserclub.pushdata.nodeserver.messages;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandShake extends NodeMessage<HandShake> {

    private Long deviceId;

    @Override
    protected Type type() {
        return Type.HS;
    }

    @Override
    protected HandShake getThis() throws Exception {
        return this;
    }
}
