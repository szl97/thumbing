package com.loserclub.shared.entity.mongo.content;

import com.loserclub.shared.entity.mongo.BaseMongoEntity;
import com.loserclub.shared.entity.mongo.content.inner.CommentPosition;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.List;

/**
 * 用于显示用户的评论记录
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:51
 */
@Document(collection = "user_comment")
@Data
public class UserComment extends BaseMongoEntity {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 评论所在的位置
     */
    private List<CommentPosition> commentPositions;
}
