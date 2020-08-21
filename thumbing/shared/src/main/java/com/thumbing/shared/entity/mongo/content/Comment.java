package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-19 10:48
 */
@Document(collection = "comment")
@Data
@FieldNameConstants
public class Comment extends MongoFullAuditedEntity {
    /**
     * 评论Id
     */
    private Long commentId;
    /**
     * 文章或帖子的Id
     */
    private String contentId;
    /**
     * 文章还是帖子
     */
    private ContentType contentType;
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
    private Integer thumbingNum;
}
