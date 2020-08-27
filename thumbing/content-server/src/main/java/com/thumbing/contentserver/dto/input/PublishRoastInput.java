package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 16:55
 */
@Data
public class PublishRoastInput implements Serializable {
    @ApiModelProperty(value = "内容")
    @NotNull(message = "内容不可为空")
    @Size(max = 800, min = 20, message = "长度限制20-800")
    private String content;
}
