package com.loserclub.shared.entity.mongo.content.inner;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 文章或动态下的评论
 * 树结构
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:24
 */
@Data
public class InnerComment implements Serializable {
    /**
     * 同级中的位置
     */
    private int id;
    /**
     * 发送方Id
     */
    private long fromId;
    /**
     * 接受方Id
     */
    private long toId;
    /**
     * 评论下的评论
     */
    private List<InnerComment> innerComments;
    /**
     * 发送方名字
     */
    private String fromName;
    /**
     * 接受方名字
     */
    private String toName;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞用户Id
     */
    private Set<Long> thumbUserIds;
    /**
     * 时间
     */
    private LocalDateTime dateTime;
}
