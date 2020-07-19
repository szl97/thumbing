package com.loserclub.auth.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 10:23
 */
@Data
public class LoginRequest implements Serializable {
    @ApiModelProperty(value = "帐户名",position = 0)
    @NotBlank(message = "帐户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码",position = 1)
    private String password;
}
