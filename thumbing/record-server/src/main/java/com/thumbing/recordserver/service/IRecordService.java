package com.thumbing.recordserver.service;

import com.thumbing.recordserver.dto.input.ChatRecordInput;
import com.thumbing.recordserver.dto.input.ReadRecordInput;
import com.thumbing.recordserver.dto.input.RemoveChatRecordInput;
import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.shared.auth.model.UserContext;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 8:38
 */
public interface IRecordService {
    /**
     * 获取指定user之间的聊天记录
     * @param input
     * @param context
     * @return
     */
    Page<ChatRecordDto> fetchRecords(ChatRecordInput input, UserContext context);
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
    Boolean readChatMessage(ReadRecordInput input, UserContext context);
    /**
     * 撤回消息
     * @param removeChatRecordInput
     * @param context
     * @return
     */
    Boolean cancelMessage(RemoveChatRecordInput removeChatRecordInput, UserContext context);
}
