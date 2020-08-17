package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.content.inner.CommentPosition;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 用于显示用户的评论记录
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:51
 */
@Document(collection = "user_comment")
@Data
@FieldNameConstants
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
