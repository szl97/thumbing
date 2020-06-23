package com.loserclub.pushdata.datacenter.messages;

import com.loserclub.pushdata.common.constants.OperationEnum;
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
public class PushReq extends NodeMessageReq<PushReq> {

    private List<String> deviceIds;

    private String name;

    private OperationEnum operation;

    @Override
    protected Type type() {
        return Type.P;
    }

    @Override
    protected PushReq getThis() throws Exception {
        return this;
    }
}
