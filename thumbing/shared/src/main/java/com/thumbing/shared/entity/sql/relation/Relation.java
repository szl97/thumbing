package com.thumbing.shared.entity.sql.relation;

import com.thumbing.shared.constants.EntityConstants;
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
@NamedEntityGraph(name = Relation.NamedEntityGraph_userInfo, attributeNodes = {
        @NamedAttributeNode("userOne"),
        @NamedAttributeNode("userTwo")
}
)
public class Relation extends SqlFullAuditedEntity {
    public static final String NamedEntityGraph_userInfo = "Relation.userInfo";
    /**
     * 关系一方的用户ID, ID小的一方为User1
     */
    private Long userIdOne;
    /**
     * 关系另一方的用户ID, ID大的一方为User2
     */
    private Long userIdTwo;
    /**
     * 标识建立关系的用户
     */
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = Fields.userIdOne, referencedColumnName = UserInfo.Fields.userId, insertable = false, updatable = false)
    private UserInfo userOne;
    /**
     * 标识关系另一方的用户
     */
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = Fields.userIdTwo, referencedColumnName = UserInfo.Fields.userId, insertable = false, updatable = false)
    private UserInfo userTwo;
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
        return  a.getId().equals(getId());
    }
}
