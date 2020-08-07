package com.thumbing.auth.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 12:53
 */
@Data
public class SignUpInput implements Serializable {
    @NotBlank(message = "用户名不可为空")
    @ApiModelProperty(value = "账户名")
    private String userName;
    @NotBlank(message = "密码不可为空")
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "电话")
    private String phoneNum;
    @ApiModelProperty(value = "邮箱地址")
    private String email;
    @NotBlank(message = "验证码不可为空")
    @ApiModelProperty(value = "验证码")
    private String validation;
    @ApiModelProperty(value = "注册设备")
    private DeviceInput deviceInput;
}
