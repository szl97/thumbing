package com.thumbing.shared.entity.sql.relation;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.sql.BaseSqlEntity;
import com.thumbing.shared.entity.sql.SqlFullAuditedEntity;
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
public class Relation extends SqlFullAuditedEntity {
    /**
     * 关系一方的用户ID, 申请添加一方为user1
     */
    private String userNameOne;
    /**
     * 关系另一方的用户ID, 同意添加一方为user2
     */
    private String userNameTwo;
    /**
     * 标识建立关系的用户
     */
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = Fields.userNameOne, referencedColumnName = UserInfo.Fields.userName, insertable = false, updatable = false)
    private UserInfo userOne;
    /**
     * 标识关系另一方的用户
     */
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = Fields.userNameTwo, referencedColumnName = UserInfo.Fields.userName, insertable = false, updatable = false)
    private UserInfo UserTwo;
    /**
     * 状态 代表持续聊天的时间到达的关系等级
     * year month week day
     */
    private String Status;
    /**
     * 持续聊天天数
     */
    private int continueDay;
    /**
     * 关系剩余时间
     * 代表多久不发消息就会取消好友关系
     */
    private int restDay;
    /**
     * 是否是黑名单
     */
    private boolean black;
    /**
     * 0代表未拉黑
     * 1代表user一方拉黑
     * 2代表target一方拉黑
     * 3代表互相拉黑
     */
    private short blackSides;
    /**
     * user1一方的昵称
     */
    private String nickNameOne;
    /**
     * user2一方的昵称
     */
    private String nickNameTwo;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Relation)) return false;
        Relation a = (Relation)object;
        return  a.getId() == getId();
    }
}
