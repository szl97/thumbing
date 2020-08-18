package com.thumbing.shared.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:25
 */
@Data
public class ChatDataMsg implements Serializable {

    /**
     * 消息Id
     */
    private Long dataId;
    /**
     * 发送方id
     */
    private Long fromUser;
    /**
     * 发送方用户名
     */
    private String fromUserName;
    /**
     * 发送方昵称
     */
    private String fromUserNickName;
    /**
     * 接受方Id
     */
    private Long toUser;
    /**
     * 接受方用户名
     */
    private String toUserName;
    /**
     * 接受方昵称
     */
    private String toUserNickName;
    /**
     * 消息内容
     */
    private String data;
    /**
     * 消息发送时间
     */
    private LocalDateTime time;
}
