package com.thumbing.recordserver.service;

import com.thumbing.recordserver.dto.input.ReadPushDataRecord;
import com.thumbing.recordserver.dto.output.PushDataDto;
import com.thumbing.shared.auth.model.UserContext;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 11:40
 */
public interface IPushDataService {
    /**
     * 获取所有推送消息
     * @param context
     * @return
     */
    List<PushDataDto> fetchAllPushDataRecords(UserContext context);

    /**
     * 推送消息已读
     * @return
     */
    Boolean readRecord(ReadPushDataRecord input, UserContext context);
}
