package com.thumbing.recordserver.persistence;

import com.thumbing.recordserver.cache.SessionRecordCache;
import com.thumbing.recordserver.dto.input.ReadChatRecord;
import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.message.ChatDataMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 18:51
 */
@Component
public class SessionPersistence {
    @Autowired
    SessionRecordCache sessionRecordCache;

    @AccessLock(value = "com.thumbing.shared.message.ChatDataMsg",
            className = "com.thumbing.shared.message.ChatDataMsg",
            fields = {
                    "getToFromUser","getToUser"
            })
    public void saveForFromUser(ChatDataMsg msg){
        SessionRecordDto dto1 =sessionRecordCache.getOne(msg.getFromUser(), msg.getToUser());
        if(dto1 == null) {
            dto1 = new SessionRecordDto();
            dto1.setTargetNickName(msg.getFromUserNickName());
            dto1.setTargetUserId(msg.getToUser());
            dto1.setNoReadNum(0);
            dto1.setTime(msg.getTime());
        }
        if(!dto1.getTime().isBefore(msg.getTime())) {
            dto1.setLastDataId(msg.getDataId());
            dto1.setTime(msg.getTime());
            dto1.setLastMessage(msg.getData().length() > 20
                    ? msg.getData().substring(0, 20)
                    : msg.getData());
            sessionRecordCache.set(msg.getFromUser(), dto1);
        }
    }

    @AccessLock(value = "com.thumbing.shared.message.ChatDataMsg",
            className = "com.thumbing.shared.message.ChatDataMsg",
            fields = {
                    "getToUser","getFromUser"
            })
    public void saveForToUser(ChatDataMsg msg){
        boolean isSave = false;
        SessionRecordDto dto2 =sessionRecordCache.getOne(msg.getToUser(), msg.getFromUser());
        if(dto2 == null) {
            dto2 = new SessionRecordDto();
            dto2.setTargetNickName(msg.getFromUserNickName());
            dto2.setTargetUserId(msg.getFromUser());
            dto2.setNoReadNum(0);
            dto2.setTime(msg.getTime());
        }
        if(!dto2.getTime().isBefore(msg.getTime())) {
            dto2.setLastDataId(msg.getDataId());
            dto2.setTime(msg.getTime());
            dto2.setLastMessage(msg.getData().length() > 20
                    ? msg.getData().substring(0, 20)
                    : msg.getData());
            isSave = true;
        }
        if (msg.getTime().isAfter(dto2.getLastReadTime())) {
            dto2.setNoReadNum(dto2.getNoReadNum() + 1);
            isSave = true;
        }
        if(isSave) {
            sessionRecordCache.set(msg.getToUser(), dto2);
        }
    }

    @AccessLock(value = "com.thumbing.shared.message.ChatDataMsg",
            className = "com.thumbing.shared.message.ChatDataMsg",
            fields = {
                    "getToUser","getFromUser"
            })
    public void saveAllInCache(ChatDataMsg msg, ReadChatRecord input){
        SessionRecordDto dto =sessionRecordCache.getOne(msg.getToUser(), msg.getFromUser());
        if(dto == null) {
            dto = new SessionRecordDto();
            dto.setTargetNickName(msg.getFromUserNickName());
            dto.setTargetUserId(msg.getFromUser());
            dto.setNoReadNum(0);
            dto.setLastReadTime(LocalDateTime.MIN);
        }
        dto.setTime(msg.getTime());
        if(msg.getTime().isAfter(dto.getLastReadTime())) {
            dto.setLastReadTime(input.getLastReadTime());
            dto.setLastMessage(msg.getData().length() > 20
                    ? msg.getData().substring(0, 20)
                    : msg.getData());
            final SessionRecordDto finalDto = dto;
            int chatRecordNums = (int)input.getMsg()
                    .parallelStream()
                    .filter(s->s.getTime().isAfter(finalDto.getLastReadTime()))
                    .count();
            int noReadNum = dto.getNoReadNum() - chatRecordNums;
            noReadNum = noReadNum < 0 ? 0 : noReadNum;
            dto.setNoReadNum(noReadNum);
        }
        sessionRecordCache.set(msg.getToUser(), dto);
    }
}
