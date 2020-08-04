package com.thumbing.auth.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 15:05
 */
@Data
public class ChangePasswordRequest implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @NotBlank(message = "用户名不可为空")
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "原密码")
    private String oldPassWord;
    @NotBlank(message = "新密码不可为空")
    @ApiModelProperty(value = "新密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String validation;
}
