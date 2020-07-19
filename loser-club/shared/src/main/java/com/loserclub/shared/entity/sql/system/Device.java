package com.loserclub.shared.entity.sql.system;

import com.loserclub.shared.entity.BaseEntity;
import com.loserclub.shared.entity.sql.SqlEditionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 13:22
 */

@Entity
@Table(name = "sys_device")
@Getter
@Setter
@FieldNameConstants
@NamedEntityGraph(name = "device.all_users",attributeNodes = {
        @NamedAttributeNode("users")
})
//user_ids, current_user_id, device_id, device_name, device_info
public class Device extends SqlEditionEntity {
    /**
     * 设备的固有Id
     */
    private String deviceId;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 设备信息
     */
    private String deviceInfo;
    /**
     * 设备上所有登录成功过的用户
     */
    @ManyToMany
    @JoinTable(name = "device_user",
            joinColumns = { @JoinColumn(name = "device_id") },
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;
    /**
     * 设备的当前用户
     */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "current_user_id", referencedColumnName = BaseEntity.Fields.id)
    private User currentUserId;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Device)) return false;
        Device a = (Device) object;
        return  a.getId() == getId();
    }
}
