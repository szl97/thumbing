package com.thumbing.shared.entity.sql.group;

import com.thumbing.shared.entity.BaseEntity;
import com.thumbing.shared.entity.sql.SqlCreationEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import com.thumbing.shared.entity.sql.system.User;
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
//user_id(fk), current, total_hours
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
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name= SqlCreationEntity.Fields.createId, referencedColumnName = BaseEntity.Fields.id, insertable = false, updatable = false)
    private User creator;
    /**
     * 成员列表
     */
    @ManyToMany
    @JoinTable(name = "chat_group_user",
            joinColumns = { @JoinColumn(name = "chat_group_id") },
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof ChatGroup)) return false;
        ChatGroup a = (ChatGroup)object;
        return  a.getId() == getId();
    }
}
