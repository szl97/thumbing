package com.thumbing.shared.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:25
 */
@Data
public class ChatDataMsg {
    private Long fromUser;

    private String fromUserName;

    private String fromUserNickName;

    private Long toUser;

    private String name;

    private String data;

    private Long sessionId;

    private LocalDateTime time;
}
