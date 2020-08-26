package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:38
 */
@Document(collection = "moments")
@Data
@FieldNameConstants
public class Moments extends MongoFullAuditedEntity {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<Long> tagIds;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞数
     */
    private Integer thumbingNum;
    /**
     * 评论数
     */
    private Integer commentsNum;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 下一个评论用户显示的昵称
     */
    private int nickNameSequence;
    /**
     * 动态中的图片在OSS中的标识
     */
    private List<String> graphIds;
    /**
     * 浏览过的用户id
     */
    private Set<Long> browseUserIds;
}
