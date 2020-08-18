package com.thumbing.recordserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 11:44
 */
@Data
public class ReadPushDataRecord implements Serializable {
    private List<PushDataInputDto> msg;
    @ApiModelProperty(value = "消息已读时间")
    @NotNull(message = "消息已读时间不可为空")
    private LocalDateTime readTime;
}
