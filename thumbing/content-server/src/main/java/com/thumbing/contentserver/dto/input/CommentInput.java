package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Stan Sai
 * @date 2020-08-26 5:03
 */
@Data
public class CommentInput implements Serializable {
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
}
