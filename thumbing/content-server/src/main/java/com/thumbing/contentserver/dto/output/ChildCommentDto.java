package com.thumbing.contentserver.dto.output;

import com.thumbing.shared.dto.output.DocumentDto;
import lombok.Data;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/19 15:31
 */
@Data
public class ChildCommentDto extends DocumentDto {
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
