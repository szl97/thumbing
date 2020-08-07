package com.thumbing.shared.entity.sql.black;

import com.thumbing.shared.entity.sql.SqlCreationEntity;
import com.thumbing.shared.entity.sql.user.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:11
 */
@Entity
@Table(name = "black_list" ,indexes = {
        @Index(columnList = "userId"),
        @Index(columnList = "targetUserId")
})
@Getter
@Setter
@FieldNameConstants
public class BlackList extends SqlCreationEntity {
    /**
     * 把对方加入黑名单的一方
     */
    private Long userId;
    /**
     * 被加入黑名单的一方
     */
    private Long targetUserId;
    @ManyToOne(targetEntity = UserInfo.class)
    @JoinColumn(name = Fields.targetUserId, referencedColumnName = UserInfo.Fields.userId, insertable = false, updatable = false)
    private UserInfo targetUserInfo;
}
