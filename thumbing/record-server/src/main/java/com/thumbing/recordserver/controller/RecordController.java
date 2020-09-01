package com.thumbing.recordserver.controller;

import com.thumbing.recordserver.dto.input.ChatRecordInput;
import com.thumbing.recordserver.dto.input.ReadChatRecord;
import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.recordserver.service.IChatService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.shared.dto.output.PageResultDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 10:34
 */
@EnableResponseAdvice
@RestController
@RequestMapping("/chat")
public class RecordController extends ThumbingBaseController {
    @Autowired
    private IChatService chatService;

    @ApiOperation("获取聊天记录")
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    @Authorize(PermissionConstants.REGISTER)
    public PageResultDto<ChatRecordDto> fetchRecords(@RequestBody @Valid ChatRecordInput input){
         return chatService.fetchRecords(input, getCurrentUser());
    }

    @ApiOperation("获取会话列表")
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    List<SessionRecordDto> fetchAllSessions(){
        return chatService.fetchAllSessions(getCurrentUser());
    }

    @ApiOperation("已读消息处理")
    @RequestMapping(method = RequestMethod.PUT)
    @Authorize(PermissionConstants.REGISTER)
    Boolean readChatMessage(@RequestBody @Valid ReadChatRecord input){
        return chatService.readChatMessage(input, getCurrentUser());
    }

}
