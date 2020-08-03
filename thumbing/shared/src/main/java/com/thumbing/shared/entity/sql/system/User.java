package com.thumbing.shared.entity.sql.system;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.sql.BaseSqlEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 11:38
 */
@Entity
@Table(name = "sys_user")
@Getter
@Setter
@FieldNameConstants
@SQLDelete(sql =  "update sys_user " + EntityConstants.DELETION)
@Where(clause = "is_delete=0")
//user_name, pwd, personal_id(fk), is_active, last_login,
// continue_day, is_access, devices, current_device_id
public class User extends SqlFullAuditedEntity {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否激活（需要手机或邮箱认证）
     */
    private boolean active;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;
    /**
     * 连续登录天数
     */
    private int continueDays;
    /**
     * 是否通过认证，通过认证的用户拥有发表言论权限
     */
    private boolean access;
    /**
     * 是否是管理员账号
     */
    private boolean admin;
    /**
     * 用户登录过的所有设备
     */
    @ManyToMany
    @JoinTable(name = "device_user",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = {@JoinColumn(name = "device_id")})
    private Set<Device> devices;
    /**
     * 用户的当前设备
     */
    @OneToOne(targetEntity = Device.class)
    @JoinColumn(name = "current_device_id", referencedColumnName = BaseSqlEntity.Fields.id)
    private Device currentDevice;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof User)) return false;
        User a = (User)object;
        return  a.getId() == getId();
    }
}