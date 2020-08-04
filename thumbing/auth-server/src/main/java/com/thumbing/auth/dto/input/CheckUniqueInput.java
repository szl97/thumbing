package com.thumbing.auth.dto.input;

import com.thumbing.auth.dto.enums.ValidationEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 15:33
 */
@Data
@ApiModel(description = "注册时输入框要验证的数据")
public class CheckUniqueInput implements Serializable {
    @NotBlank(message = "验证类型不可为空")
    @ApiModelProperty(value = "验证类型",example = "Phone, Email, UserName")
    private ValidationEnum type;
    @NotBlank(message = "验证数据不可为空")
    @ApiModelProperty(value = "待验证数据")
    private String data;
}
