package com.thumbing.shared.entity.sql.group;

import com.thumbing.shared.entity.sql.BaseSqlEntity;
import com.thumbing.shared.entity.sql.SqlCreationEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import com.thumbing.shared.entity.sql.user.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/16 16:51
 */
@Entity
@Table(name = "chat_group")
@Getter
@Setter
@FieldNameConstants
public class ChatGroup extends SqlFullAuditedEntity {
    /**
     * 名字
     */
    private String name;
    /**
     * 主题
     */
    private String topic;
    /**
     * 标签
     */
    private String tags;
    /**
     * 存在总时长
     */
    private short totalHours;
    /**
     * 当前是否有效
     */
    private boolean current;
    /**
     * 创建人
     */
    private Long createId;
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name= Fields.createId, referencedColumnName = UserInfo.Fields.userId, insertable = false, updatable = false)
    private UserInfo creator;
    /**
     * 成员列表
     */
    @ManyToMany
    @JoinTable(name = "chat_group_user",
            joinColumns = { @JoinColumn(name = "chat_group_id") },
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<UserInfo> users;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof ChatGroup)) return false;
        ChatGroup a = (ChatGroup)object;
        return  a.getId() == getId();
    }
}
