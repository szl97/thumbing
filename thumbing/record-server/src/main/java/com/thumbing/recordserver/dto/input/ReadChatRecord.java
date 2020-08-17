package com.thumbing.recordserver.dto.input;

import com.thumbing.shared.message.ChatDataMsg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 11:28
 */
@Data
public class ReadChatRecord implements Serializable {
    List<ChatDataMsg> msg;
    @ApiModelProperty(value = "最后阅读时间")
    @NotNull(message = "最后阅读时间不可为空")
    LocalDateTime lastReadTime;
}
