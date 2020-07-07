package com.loserclub.pushdata.datacenter.messages;

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
public class ChatData extends NodeMessageReq<ChatData> {

    private Long fromUser;

    private List<Long> toUsers;

    private List<Long> deviceIds;

    private String name;

    private String data;

    @Override
    protected Type type() {
        return Type.CD;
    }

    @Override
    protected ChatData getThis() throws Exception {
        return this;
    }
}
