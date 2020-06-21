package com.loserclub.pushdata.datacenter.Messages.recvforsync;

import com.loserclub.pushdata.common.constants.OperationEnum;
import com.loserclub.pushdata.datacenter.Messages.NodeMessageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
