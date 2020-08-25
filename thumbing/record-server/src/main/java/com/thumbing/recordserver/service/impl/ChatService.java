package com.thumbing.recordserver.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.github.dozermapper.core.Mapper;
import com.thumbing.recordserver.cache.ChatRecordCache;
import com.thumbing.recordserver.cache.SessionRecordCache;
import com.thumbing.recordserver.dto.input.ChatMsgInputDto;
import com.thumbing.recordserver.dto.input.ChatRecordInput;
import com.thumbing.recordserver.dto.input.ReadChatRecord;
import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.recordserver.persistence.RecordPersistence;
import com.thumbing.recordserver.persistence.SessionPersistence;
import com.thumbing.recordserver.service.IChatService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.repository.mongo.record.IChatRecordRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 11:40
 */
@Service
@Transactional
public class ChatService extends BaseMongoService<ChatRecord, IChatRecordRepository> implements IChatService {
    @Autowired
    private RecordPersistence recordPersistence;
    @Autowired
    private SessionPersistence sessionPersistence;
    @Autowired
    private ChatRecordCache chatRecordCache;
    @Autowired
    private SessionRecordCache sessionRecordCache;
    @Autowired
    private Mapper mapper;

    /**
     * 先从缓存中获取
     * 如果不存在查询mongo
     * @param input
     * @param context
     * @return
     */
    @Override
    public PageResultDto<ChatRecordDto> fetchRecords(ChatRecordInput input, UserContext context) {
        Long userId1 = Math.min(input.getTargetUser(), context.getId());
        Long userId2 = Math.max(input.getTargetUser(), context.getId());
        if(input.getPosition() > 0){
            int len = chatRecordCache.size(userId1, userId2).intValue();
            int end = Math.min(len, input.getPosition());
            int start = Math.max(end - input.getPageSize(), 0);
            List<ChatRecordDto> recordDtoList = chatRecordCache.getRecord(userId1, userId2, start, end);
            if(ArrayUtil.isNotEmpty(recordDtoList)){
                return new PageResultDto<>(0, recordDtoList, start - 1);
            }
        }

        Pageable pageable = input.getPageable();
        Sort sort = Sort.by(MongoCreationEntity.Fields.createTime);
        Page<ChatRecord> recordPage = repository.findAllByUserId1AndUserId2AndCreateTimeIsAfter(userId1,userId2,input.getEarlyTime(),
                PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), sort));
        PageResultDto<ChatRecordDto> resultDto = DozerUtils.mapToPagedResultDtoSync(mapper, recordPage, ChatRecordDto.class, (s,t)->{
            t.setTime(s.getCreateTime());
        });
        resultDto.setPosition(-1);
        return resultDto;
    }

    /**
     * 先从缓存中获取
     * 如果不存在查询mongo
     * @param context
     * @return
     */
    @Override
    public List<SessionRecordDto> fetchAllSessions(UserContext context) {
        List<SessionRecordDto> sessionRecordDtoList = sessionRecordCache.getAll(context.getId());
        if(ArrayUtil.isNotEmpty(sessionRecordDtoList)){
            return sessionRecordDtoList;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, MongoCreationEntity.Fields.createTime, ChatRecord.Fields.fromId);
        List<ChatRecord> chatRecordList = repository.findAllByToIdAndRead(context.getId(),false, sort);
        if(ArrayUtil.isEmpty(chatRecordList)) return null;
        sessionRecordDtoList = new ArrayList<>();
        Long id = null;
        String nickName = null;
        String lastMsg = null;
        Long lastDataId = null;
        LocalDateTime lastMsgTime = null;
        int count = 0;
        for(ChatRecord record : chatRecordList){
            if(id != null && !id.equals(record.getFromId())){
                SessionRecordDto s = new SessionRecordDto();
                s.setLastReadTime(LocalDateTime.MIN);
                s.setLastDataId(lastDataId);
                s.setLastMessage(lastMsg);
                s.setNoReadNum(count);
                s.setTime(lastMsgTime);
                s.setTargetUserId(id);
                s.setTargetNickName(nickName);
                sessionRecordDtoList.add(s);
            }
            if(id == null || !id.equals(record.getFromId())){
                id = record.getFromId();
                nickName = record.getFromNickName();
                lastDataId = record.getDataId();
                lastMsg = record.getContent().substring(0,20);
                count = 1;
                lastMsgTime = record.getCreateTime();
            }
            else{
                count++;
            }
        }
        sessionRecordDtoList.parallelStream().forEach(s->sessionRecordCache.set(context.getId(), s));
        return sessionRecordDtoList;
    }

    @Override
    public Boolean readChatMessage(ReadChatRecord input, UserContext context) {
        if(ArrayUtil.isEmpty(input.getMsg())){
            return false;
        }
        //todo:处理会话缓存
        ChatMsgInputDto msg = input.getMsg().get(input.getMsg().size() - 1);
        sessionPersistence.saveAllInCache(msg,input);

        //todo:更改Mongo
        input.getMsg().parallelStream().map(s->convert(s,true,false)).forEach(
           e-> recordPersistence.saveInDb(e)
        );
        return true;
    }

    private ChatRecord convert(ChatMsgInputDto msg, boolean isRead, boolean isCancel){
        ChatRecord record = new ChatRecord();
        record.setDataId(msg.getDataId());
        record.setContent(msg.getData());
        record.setFromId(msg.getFromUser());
        record.setToId(msg.getToUser());
        record.setRead(isRead);
        record.setCancel(isCancel);
        record.setCreateTime(msg.getTime());
        Long id1 = msg.getFromUser() < msg.getToUser() ? msg.getFromUser() : msg.getToUser();
        Long id2 = id1.equals(msg.getFromUser()) ? msg.getToUser() : msg.getFromUser();
        record.setUserId1(id1);
        record.setUserId2(id2);
        return record;
    }
}
