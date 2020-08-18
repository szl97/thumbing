package com.thumbing.recordserver.service;

import com.thumbing.recordserver.dto.input.ChatRecordInput;
import com.thumbing.recordserver.dto.input.ReadChatRecord;
import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 8:38
 */
public interface IChatService {
    /**
     * 获取指定user之间的聊天记录
     * @param input
     * @param context
     * @return
     */
    PageResultDto<ChatRecordDto> fetchRecords(ChatRecordInput input, UserContext context);
    /**
     * 获取会话列表
     * @param context
     * @return
     */
    List<SessionRecordDto> fetchAllSessions(UserContext context);
    /**
     * 已读消息
     * @return
     */
    Boolean readChatMessage(ReadChatRecord input, UserContext context);
}
