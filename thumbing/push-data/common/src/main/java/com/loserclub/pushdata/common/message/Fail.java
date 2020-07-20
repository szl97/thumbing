package com.loserclub.pushdata.common.message;

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
public class Fail extends NodeMessage {
    @Override
    protected Type type() {
        return Type.F;
    }

    @Override
    protected Object getThis() throws Exception {
        return this;
    }
}
