package com.thumbing.shared.entity.mongo.content.inner;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 文章或动态下的评论
 * 树结构，只有一层子树，评论下的评论全部打平显示
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:24
 */
@Data
public class InnerComment implements Serializable {
    /**
     * 位置(是否需要显示在同级中的位置？)
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
    /**
     * 是否是第一层
     */
    private boolean isParent;
}
