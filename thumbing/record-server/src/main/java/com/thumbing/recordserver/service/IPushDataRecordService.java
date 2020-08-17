package com.thumbing.recordserver.service;

import com.thumbing.recordserver.dto.input.ReadPushDataRecord;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import org.springframework.data.domain.Page;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 11:40
 */
public interface IPushDataRecordService {
    /**
     * 获取所有推送消息
     * @param context
     * @return
     */
    PageResultDto<PushDataRecord> fetchAllPushDataRecords(UserContext context);

    /**
     * 推送消息已读
     * @return
     */
    Boolean readRecord(ReadPushDataRecord input, UserContext context);
}
