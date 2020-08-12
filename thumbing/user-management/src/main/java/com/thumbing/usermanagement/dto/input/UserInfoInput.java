package com.thumbing.usermanagement.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 9:13
 */
@Data
public class UserInfoInput implements Serializable {
    @NotBlank(message = "昵称不可为空")
    private String nickName;
}
