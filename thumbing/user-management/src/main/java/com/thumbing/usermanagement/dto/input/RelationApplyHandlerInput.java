package com.thumbing.usermanagement.dto.input;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:50
 */
@Data
public class RelationApplyHandlerInput extends EntityDto {
    @NotBlank(message = "昵称不可为空")
    @ApiModelProperty(value = "处理请求方昵称")
    private String nickName1;
    @NotBlank(message = "对方昵称不可为空")
    @ApiModelProperty(value = "对方昵称")
    private String nickName2;
    @ApiModelProperty(value = "是否同意")
    private boolean approve;
}
