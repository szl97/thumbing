package com.thumbing.shared.message;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:25
 */
@Data
public class ChatDataMsg implements Serializable {
    private Long fromUser;

    private String fromUserName;

    private String fromUserNickName;

    private Long toUser;

    private String toUserName;

    private String toUserNickName;

    private String data;

    private String sessionId;

    private LocalDateTime time;
}