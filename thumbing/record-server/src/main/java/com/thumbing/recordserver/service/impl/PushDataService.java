package com.thumbing.recordserver.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.github.dozermapper.core.Mapper;
import com.thumbing.recordserver.dto.input.ReadPushDataRecord;
import com.thumbing.recordserver.dto.output.PushDataDto;
import com.thumbing.recordserver.persistence.PushDataPersistence;
import com.thumbing.recordserver.service.IPushDataService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.repository.mongo.record.IPushDataRecordRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 9:06
 */
@Service
public class PushDataService extends BaseMongoService<PushDataRecord, IPushDataRecordRepository> implements IPushDataService {
    @Autowired
    private PushDataPersistence persistence;
    @Autowired
    private Mapper mapper;

    @Override
    public List<PushDataDto> fetchAllPushDataRecords(UserContext context) {
        List<PushDataRecord> records = repository.findAllByToUserIdAndRead(context.getId(), false, Sort.by(MongoCreationEntity.Fields.createTime));
        if(ArrayUtil.isEmpty(records)) return null;
        return DozerUtils.mapList(mapper, records, PushDataDto.class, (s,t)->{
            t.setTime(s.getCreateTime());
        });
    }

    @Override
    public Boolean readRecord(ReadPushDataRecord input, UserContext context) {
        if(ArrayUtil.isEmpty(input.getMsg())) return false;
        input.getMsg().parallelStream().forEach(
                s->{
                    PushDataRecord record = mapper.map(s, PushDataRecord.class);
                    record.setReadTime(input.getReadTime());
                    record.setRead(true);
                    record.setCreateTime(s.getTime());
                    persistence.saveInDb(record);
                }
        );
        return true;
    }
}
