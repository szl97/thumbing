package com.thumbing.shared.auth.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

/**
 * 用户请求上下文对象
 * @Author: Stan Sai
 * @Date: 2020/7/13 18:36
 */
@Data
public class UserContext implements Serializable {
    //用户主键
    private Long id;
    //姓名
    private String name;
    //角色列表
    private List<GrantedAuthority> authorities;
}
