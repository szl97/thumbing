package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import com.thumbing.shared.entity.mongo.content.inner.InnerComment;
import com.thumbing.shared.entity.mongo.common.NickUser;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:38
 */
@Document(collection = "moments")
@Data
@SQLDelete(sql =  "update moments " + EntityConstants.NO_VERSION_DELETION)
@Where(clause = "is_delete=0")
//user_id, tags_ids, content, total_days, thumb_user_ids, comments, next_nick_name, graph_ids, browse_user_ids
public class Moments extends MongoFullAuditedEntity {
    /**
     * 用户id
     */
    private long user_id;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<Long> tags_ids;
    /**
     * 可见天数
     */
    private int totalDays;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 动态下的评论
     */
    private List<InnerComment> innerComments;
    /**
     * 参与评论的所有用户
     */
    private Set<NickUser> nickUsers;
    /**
     * 下一个评论用户显示的昵称
     */
    private String nextNickName;
    /**
     * 动态中的图片在OSS中的标识
     */
    private List<String> graphIds;
    /**
     * 浏览过的用户id
     */
    private Set<Long> browseUserIds;
}
