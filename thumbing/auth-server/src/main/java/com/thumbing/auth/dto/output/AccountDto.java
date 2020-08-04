package com.thumbing.auth.dto.output;

import com.thumbing.shared.dto.output.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 10:07
 */
@Data
public class AccountDto extends EntityDto {
    @ApiModelProperty(value = "帐户名")
    private String userName;
    @ApiModelProperty(value = "是否激活（即是否通过手机或邮箱认证）")
    private boolean active;
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLogin;
    @ApiModelProperty(value = "连续登录天数")
    private int continueDays;
    @ApiModelProperty(value = "是否通过认证（通过认证的用户拥有发表言论权限）")
    private boolean access;
}
