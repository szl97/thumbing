package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:49
 */
@Document(collection = "roast")
@Data
@SQLDelete(sql =  "update roast " + EntityConstants.NO_VERSION_DELETION)
@Where(clause = "is_delete=0")
@FieldNameConstants
public class Roast extends MongoFullAuditedEntity {
    /**
     * 用户id
     */
    @Indexed
    private Long userId;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞数
     */
    private Integer thumbingNum;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
}
