package com.thumbing.pushdata.common.message;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@NoArgsConstructor
public class Ping extends NodeMessage<Ping> {
    @Override
    protected Type type() {
        return Type.PI;
    }

    @Override
    protected Ping getThis() throws Exception {
        return this;
    }
}
