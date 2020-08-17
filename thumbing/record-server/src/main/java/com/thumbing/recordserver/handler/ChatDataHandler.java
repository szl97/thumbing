package com.thumbing.recordserver.handler;

import com.thumbing.recordserver.cache.ChatRecordCache;
import com.thumbing.recordserver.cache.SessionRecordCache;
import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.message.ChatDataMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 11:21
 */
@Component
public class ChatDataHandler {
    @Autowired
    RecordPersistence recordPersistence;
    @Autowired
    ChatRecordCache chatRecordCache;
    @Autowired
    SessionRecordCache sessionRecordCache;

    public void handleSession(ChatDataMsg msg) {
        //todo:写入缓存
        SessionRecordDto dto1 =sessionRecordCache.getOne(msg.getFromUser(), msg.getToUser());
        if(dto1 == null) {
            dto1 = new SessionRecordDto();
            dto1.setTargetUserId(msg.getToUser());
            dto1.setNoReadNum(0);
        }
        dto1.setTime(msg.getTime());
        dto1.setLastMessage(msg.getData().length() > 20
                ? msg.getData().substring(0, 20)
                : msg.getData());
        sessionRecordCache.set(msg.getFromUser(), dto1);
        SessionRecordDto dto2 =sessionRecordCache.getOne(msg.getToUser(), msg.getFromUser());
        if(dto2 == null) {
            dto2 = new SessionRecordDto();
            dto2.setTargetUserId(msg.getFromUser());
            dto2.setNoReadNum(0);
        }
        dto2.setTime(msg.getTime());
        dto2.setLastMessage(msg.getData().length() > 20
                ? msg.getData().substring(0, 20)
                : msg.getData());
        if(msg.getTime().isAfter(dto2.getLastReadTime())) {
            dto2.setNoReadNum(dto2.getNoReadNum() + 1);
        }
        sessionRecordCache.set(msg.getToUser(), dto2);
    }

    public void handleRecord(ChatDataMsg msg){
        //todo:写入缓存
        ChatRecordDto dto = new ChatRecordDto();
        dto.setContent(msg.getData());
        dto.setFromId(msg.getFromUser());
        dto.setId(msg.getSessionId());
        dto.setToId(msg.getToUser());
        dto.setTime(msg.getTime());
        Long id1 = msg.getFromUser() < msg.getToUser() ? msg.getFromUser() : msg.getToUser();
        Long id2 = id1 == msg.getFromUser() ? msg.getToUser() : msg.getFromUser();
        dto.setUserId1(id1);
        dto.setUserId2(id2);
        chatRecordCache.setRecord(dto);

        //todo:写入mongo
        ChatRecord record = new ChatRecord();
        record.setContent(msg.getData());
        record.setFromId(msg.getFromUser());
        record.setToId(msg.getToUser());
        record.setRead(false);
        record.setCancel(false);
        record.setCreateTime(msg.getTime());
        id1 = msg.getFromUser() < msg.getToUser() ? msg.getFromUser() : msg.getToUser();
        id2 = id1 == msg.getFromUser() ? msg.getToUser() : msg.getFromUser();
        record.setUserId1(id1);
        record.setUserId2(id2);
        recordPersistence.saveInDb(record);
    }
}
