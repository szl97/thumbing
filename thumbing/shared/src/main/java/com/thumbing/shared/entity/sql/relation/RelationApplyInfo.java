package com.thumbing.shared.entity.sql.relation;

import com.thumbing.shared.entity.sql.SqlCreationEntity;
import com.thumbing.shared.entity.sql.user.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:36
 */
@Entity
@Table(name = "relation_apply_info", indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "targetUserId")
})
@Getter
@Setter
@FieldNameConstants
@Where(clause = "approve=false and reject=false")
@NamedEntityGraph(name = RelationApplyInfo.NamedEntityGraph_userInfo, attributeNodes = {
            @NamedAttributeNode("userInfo")
   }
)
public class RelationApplyInfo extends SqlCreationEntity {
    public static final String NamedEntityGraph_userInfo = "RelationApplyInfo.userInfo";
    /**
     * 申请发送方
     */
    private Long userId;
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = Fields.userId, referencedColumnName = UserInfo.Fields.userId, insertable = false, updatable = false)
    private UserInfo userInfo;
    /**
     * 申请接收方
     */
    private Long targetUserId;
    /**
     * 备注消息
     */
    private String remark;
    /**
     * 是否接受
     */
    private boolean approve;
    /**
     * 是否拒绝
     */
    private boolean reject;
}
