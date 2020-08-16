package com.thumbing.recordserver.handler;

import com.thumbing.shared.entity.mongo.common.NickUser;
import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.entity.mongo.record.ChatSession;
import com.thumbing.shared.message.ChatDataMsg;
import com.thumbing.shared.repository.mongo.record.IChatRecordRepository;
import com.thumbing.shared.repository.mongo.record.IChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 11:21
 */
@Component
public class ChatDataHandler {

    @Autowired
    IChatSessionRepository chatSessionRepository;
    @Autowired
    IChatRecordRepository chatRecordRepository;
    @Autowired
    SessionPersistence persistence;

    public void handleSession(ChatDataMsg msg) {
        //todo:加入缓存
        ChatSession session = null;
        if (msg.getSessionId() != null) {
            session = chatSessionRepository.findById(msg.getSessionId()).orElse(null);
        }
        if (session == null) {
            session = new ChatSession();
            Long id1 = msg.getFromUser() < msg.getToUser() ? msg.getFromUser() : msg.getToUser();
            Long id2 = id1 == msg.getFromUser() ? msg.getToUser() : msg.getFromUser();
            session.setUserId1(id1);
            session.setUserId2(id2);
            Set<NickUser> users = new HashSet<>();
            NickUser nickUser1 = new NickUser();
            nickUser1.setUserId(msg.getFromUser());
            nickUser1.setNickName(msg.getFromUserNickName());
            NickUser nickUser2 = new NickUser();
            nickUser2.setUserId(msg.getToUser());
            nickUser2.setNickName(msg.getToUserNickName());
            users.add(nickUser1);
            users.add(nickUser2);
            session.setUsers(users);
            session.setCreateTime(LocalDateTime.now());
        }
        session.setLastTime(msg.getTime());
        session.setLastMessage(msg.getData());
        persistence.saveInDb(session);
    }

    public void handleRecord(ChatDataMsg msg){
        //todo:加入缓存
        ChatRecord record = new ChatRecord();
        record.setContent(msg.getData());
        record.setFromId(msg.getFromUser());
        record.setToId(msg.getToUser());
        record.setRead(false);
        record.setCreateTime(msg.getTime());
        Long id1 = msg.getFromUser() < msg.getToUser() ? msg.getFromUser() : msg.getToUser();
        Long id2 = id1 == msg.getFromUser() ? msg.getToUser() : msg.getFromUser();
        record.setUserId1(id1);
        record.setUserId2(id2);
        chatRecordRepository.save(record);
    }
}
