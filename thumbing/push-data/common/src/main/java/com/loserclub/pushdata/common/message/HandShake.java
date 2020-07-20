package com.loserclub.pushdata.common.message;


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
    protected DefinedMessage.Type type() {
        return DefinedMessage.Type.HS;
    }

    @Override
    protected HandShake getThis() throws Exception {
        return this;
    }
}
