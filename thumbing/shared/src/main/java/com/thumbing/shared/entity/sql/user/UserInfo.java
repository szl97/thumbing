package com.thumbing.shared.entity.sql.user;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.sql.BaseSqlEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import com.thumbing.shared.entity.sql.personal.Personal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 11:38
 */
@Entity
@Table(name = "user_info", indexes = {
        @Index(name = "userId", columnList = "userId", unique = true),
        @Index(name = "userName", columnList = "userName"),
})
@Getter
@Setter
@FieldNameConstants
@SQLDelete(sql =  "update user_info " + EntityConstants.DELETION)
@Where(clause = "is_delete=0")
@NamedEntityGraph(name = UserInfo.personalNamedGraph, attributeNodes = {
        @NamedAttributeNode("personal")
})
public class UserInfo extends SqlFullAuditedEntity {
    public static final String personalNamedGraph = "personalNamedGraph";
    /**
     * 用户Id
     */
    private Long userId;
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
    private Long personalId;
    @OneToOne(targetEntity = Personal.class)
    @JoinColumn(name = Fields.personalId, referencedColumnName = BaseSqlEntity.Fields.id, insertable = false, updatable = false, unique = true)
    private Personal personal;


    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof com.thumbing.shared.entity.sql.system.User)) return false;
        com.thumbing.shared.entity.sql.system.User a = (com.thumbing.shared.entity.sql.system.User)object;
        return  a.getId() == getId();
    }
}