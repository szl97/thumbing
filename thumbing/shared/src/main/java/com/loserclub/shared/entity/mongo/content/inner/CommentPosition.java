package com.loserclub.shared.entity.mongo.content.inner;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:55
 */
@Data
//content_id, type, position
public class CommentPosition implements Serializable {
    /**
     * 评论所在的内容id
     */
    private String topicId;
    /**
     * 内容的类型：A（文章）或M（动态）
     */
    private String type;
    /**
     * 内容中的位置
     * 数组的长度-1代表层数
     * 每个位置的数字代表每一层的定位
     */
    private int[] position;
    /**
     * 时间
     */
    private LocalDateTime dateTime;
}
