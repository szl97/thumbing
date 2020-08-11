package com.thumbing.shared.entity.mongo.group;

import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.common.NickUser;
import com.thumbing.shared.entity.sql.user.UserInfo;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/16 16:51
 */

@Document(collection = "chat_group")
@Data
@FieldNameConstants
public class ChatGroup extends MongoCreationEntity {
    /**
     * 名字
     */
    private String name;
    /**
     * 主题
     */
    private String topic;
    /**
     * 标签
     */
    private String tags;
    /**
     * 存在总时长
     */
    private short totalHours;
    /**
     * 当前是否有效
     */
    private boolean current;
    /**
     * 创建人
     */
    private Long createId;

    private NickUser creator;
    /**
     * 成员列表
     */
    private Set<NickUser> users;
    /**
     * 聊天记录
     */
    private List<GroupRecord> records;

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof ChatGroup)) return false;
        ChatGroup a = (ChatGroup)object;
        return  a.getId() == getId();
    }

    @Data
    private class GroupRecord extends BaseMongoEntity {
        private Long fromUserId;
        private String fromNickName;
        private String data;
    }
}
