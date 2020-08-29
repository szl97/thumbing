package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 17:35
 */
@Document(collection = "article_content")
@Data
@FieldNameConstants
public class ArticleContent extends MongoFullAuditedEntity {
    private String content;
    @Indexed
    private String articleId;
}
