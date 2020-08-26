package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 10:10
 */
@Data
public class UpdateMomentsInput extends MomentsIdInput {
    @ApiModelProperty(value = "内容")
    @NotNull(message = "内容不可为空")
    @Size(max = 1200, min = 30)
    private String content;
}
