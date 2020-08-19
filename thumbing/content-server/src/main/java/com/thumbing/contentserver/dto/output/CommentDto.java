package com.thumbing.contentserver.dto.output;

import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/19 15:29
 */
@Data
public class CommentDto extends DocumentDto {
    /**
     * 评论Id
     */
    private Long commentId;
    /**
     * 文章或帖子的Id
     */
    private String contentId;
    /**
     * 内容类型
     */
    private ContentType type;
    /**
     * 发送方Id
     */
    private Long fromUserId;
    /**
     * 发表方昵称
     */
    private String fromNickName;
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
    /**
     * 子评论
     */
    List<ChildComment> childComments;
}
