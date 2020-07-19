package com.loserclub.shared.entity.mongo.content;

import com.loserclub.shared.entity.mongo.MongoFullAuditedEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:49
 */
@Document(collection = "roast")
@Data
//user_id, content, total_days, is_fishing, thumb_user_ids
public class Roast extends MongoFullAuditedEntity {
    /**
     * 用户id
     */
    private long user_id;
    /**
     * 标题
     */
    private String title;
    /**
     * 可见天数
     */
    private int totalDays;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 不可见后是否可以在一段时间后被打捞
     */
    private boolean fishing;
}
