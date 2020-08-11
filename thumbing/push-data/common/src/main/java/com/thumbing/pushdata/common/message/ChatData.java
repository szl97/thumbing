package com.thumbing.pushdata.common.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-08-11 20:54
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatData extends NodeMessage<ChatData> {
    private Long fromUser;

    private String fromUserName;

    private String fromUserNickName;

    private Long toUser;

    private String name;

    private String data;

    private Long sessionId;

    private LocalDateTime time;

    private boolean last;
    @Override
    protected Type type() {
        return Type.CD;
    }

    @Override
    protected ChatData getThis() {
        return this;
    }
}
