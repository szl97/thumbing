package com.thumbing.recordserver.handler;

import com.thumbing.recordserver.cache.ChatRecordCache;
import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.recordserver.persistence.RecordPersistence;
import com.thumbing.recordserver.persistence.SessionPersistence;
import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.message.ChatDataMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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
    SessionPersistence sessionPersistence;

    public void handleSession(ChatDataMsg msg) {
        //todo:写入缓存
        CompletableFuture completableFuture1 = CompletableFuture.runAsync(()->sessionPersistence.saveForFromUser(msg));
        CompletableFuture completableFuture2 = CompletableFuture.runAsync(()->sessionPersistence.saveForToUser(msg));
        CompletableFuture.allOf(completableFuture1,completableFuture2);
    }

    public void handleRecord(ChatDataMsg msg){
        //todo:写入缓存
        ChatRecordDto dto = new ChatRecordDto();
        dto.setDataId(msg.getDataId());
        dto.setContent(msg.getData());
        dto.setFromId(msg.getFromUser());
        dto.setToId(msg.getToUser());
        dto.setTime(msg.getTime());
        dto.setFromUserName(msg.getFromUserName());
        dto.setToUserName(msg.getToUserName());
        dto.setFromNickName(msg.getFromUserNickName());
        dto.setToNickName(msg.getToUserNickName());
        Long id1 = msg.getFromUser() < msg.getToUser() ? msg.getFromUser() : msg.getToUser();
        Long id2 = id1.equals(msg.getFromUser()) ? msg.getToUser() : msg.getFromUser();
        dto.setUserId1(id1);
        dto.setUserId2(id2);
        chatRecordCache.setRecord(dto);

        //todo:写入mongo
        ChatRecord record = new ChatRecord();
        record.setDataId(msg.getDataId());
        record.setContent(msg.getData());
        record.setFromId(msg.getFromUser());
        record.setToId(msg.getToUser());
        record.setFromUserName(msg.getFromUserName());
        record.setToUserName(msg.getToUserName());
        record.setFromNickName(msg.getFromUserNickName());
        record.setToNickName(msg.getToUserNickName());
        record.setRead(false);
        record.setCancel(false);
        record.setCreateTime(msg.getTime());
        record.setUserId1(id1);
        record.setUserId2(id2);
        recordPersistence.saveInDb(record);
    }
}
