package com.thumbing.pushdata.common.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Confirm extends NodeMessage<Confirm> {

    private String name;

    private String nodeIpWithPort;

    @Override
    protected Type type() {
        return Type.C;
    }

    @Override
    protected Confirm getThis() {
        return this;
    }
}
