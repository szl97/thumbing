package com.thumbing.recordserver.handler;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.record.ChatSession;
import com.thumbing.shared.repository.mongo.record.IChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 14:49
 */
@Component
public class SessionPersistence {
    @Autowired
    IChatSessionRepository chatSessionRepository;

    @AccessLock(value = "com.thumbing.shared.entity.mongo.record.ChatSession",
    className = "com.thumbing.shared.entity.mongo.record.ChatSession",
    fields = {
            "getUserId1","getUserId2"
    })
    public ChatSession saveInDb(ChatSession chatSession){
        ChatSession session = null;
        if(chatSession.getId() != null) {
            session = chatSessionRepository.findById(chatSession.getId()).orElse(null);
        }
        if(session != null && session.getLastTime().isBefore(chatSession.getLastTime())) {
            return chatSessionRepository.save(chatSession);
        }
        else {
            return session;
        }
    }
}
