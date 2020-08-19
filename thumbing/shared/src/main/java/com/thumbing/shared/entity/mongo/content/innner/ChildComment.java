package com.thumbing.shared.entity.mongo.content.innner;

import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-19 13:15
 */
@Data
public class ChildComment implements Serializable {
    /**
     * 评论Id
     */
    private Long commentId;
    /**
     * 父评论Id
     */
    private Long parentCommentId;
    /**
     * 发送方Id
     */
    private Long fromUserId;
    /**
     * 接收方Id
     */
    private Long toUserId;
    /**
     * 发表方昵称
     */
    private String fromNickName;
    /**
     * 接收方昵称
     */
    private String toNickName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 点赞数
     */
    private int thumbingNum;
}
