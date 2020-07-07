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
public class PushData extends NodeMessageReq<PushData> {

    private Long fromUser;

    private List<Long> toUsers;

    private List<Long> deviceIds;

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
