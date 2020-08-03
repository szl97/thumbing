package com.thumbing.shared.entity.sql.user;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.BaseEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import com.thumbing.shared.entity.sql.group.ChatGroup;
import com.thumbing.shared.entity.sql.personal.Personal;
import com.thumbing.shared.entity.sql.relation.Relation;
import com.thumbing.shared.entity.sql.system.Device;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 11:38
 */
@Entity
@Table(name = "user_info")
@Getter
@Setter
@FieldNameConstants
@SQLDelete(sql =  "update user_info " + EntityConstants.DELETION)
@Where(clause = "is_delete=0")
@NamedEntityGraph(name = "withPersonalAndChatGroups",attributeNodes = {
        @NamedAttributeNode("personal"),
        @NamedAttributeNode("chatGroups")
})
//nick_name, pwd, personal_id(fk), relation_id(fk), chat_group_num
public class UserInfo extends SqlFullAuditedEntity {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户的个人信息
     */
    @OneToOne(targetEntity = Personal.class)
    @JoinColumn(name = "personal_id", referencedColumnName = BaseEntity.Fields.id)
    private Personal personal;
    /**
     * 关系列表
     */
    @OneToMany(targetEntity = Relation.class, mappedBy = Relation.Fields.user, cascade = CascadeType.ALL)
    private List<Relation> relations;
    /**
     * 加入聊天室列表
     */
    @ManyToMany
    @JoinTable(name = "chat_group_user",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = {@JoinColumn(name = "chat_group_id")})
    private Set<ChatGroup> chatGroups;
    /**
     * 创建的聊天室列表
     */
    @OneToMany(targetEntity = ChatGroup.class, mappedBy = ChatGroup.Fields.creator)
    private Set<ChatGroup> createChatGroups;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof com.thumbing.shared.entity.sql.system.User)) return false;
        com.thumbing.shared.entity.sql.system.User a = (com.thumbing.shared.entity.sql.system.User)object;
        return  a.getId() == getId();
    }
}