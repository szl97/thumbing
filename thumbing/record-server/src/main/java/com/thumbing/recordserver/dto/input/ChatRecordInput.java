package com.thumbing.recordserver.dto.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.input.PagedAndSortedInput;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:02
 */
@Data
public class ChatRecordInput extends PagedAndSortedInput {
    @ApiModelProperty(value = "对方用户Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @NotNull(message = "对方Id不可为空")
    private Long targetUser;
    @ApiModelProperty(value = "已拉取聊天记录中的最早的时间")
    @NotNull(message = "已拉取聊天记录中的最早的时间不可为空")
    private LocalDateTime earlyTime;
}
