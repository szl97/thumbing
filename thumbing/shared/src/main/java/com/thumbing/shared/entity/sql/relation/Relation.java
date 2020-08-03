package com.thumbing.shared.entity.sql.relation;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.BaseEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
import com.thumbing.shared.entity.sql.system.User;
import com.thumbing.shared.entity.sql.user.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/16 16:33
 */
@Entity
@Table(name = "relation")
@Getter
@Setter
@FieldNameConstants
@SQLDelete(sql =  "update relation " + EntityConstants.DELETION)
@Where(clause = "is_delete=0")
//user_id(fk), target_id(fk), status, continue_day, rest_day, is_black, nick_name
public class Relation extends SqlFullAuditedEntity {
    /**
     * 标识建立关系的用户
     */
    @ManyToOne(targetEntity = UserInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = BaseEntity.Fields.id)
    private UserInfo user;
    /**
     * 标识关系另一方的用户
     */
    @OneToOne(targetEntity = UserInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_user_id", referencedColumnName = BaseEntity.Fields.id)
    private UserInfo targetUser;
    /**
     * 状态 代表持续聊天的时间到达的关系等级
     * year month week day
     */
    private String Status;
    /**
     * 持续聊天天数
     */
    private int continue_day;
    /**
     * 关系剩余时间
     * 代表多久不发消息就会取消好友关系
     */
    private int rest_day;
    /**
     * 是否是黑名单
     */
    private boolean black;
    /**
     * 昵称
     */
    private String nickName;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Relation)) return false;
        Relation a = (Relation)object;
        return  a.getId() == getId();
    }
}
