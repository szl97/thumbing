package com.loserclub.pushdata.common.repository;

import com.loserclub.pushdata.common.entity.mongo.BaseMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IBaseMongoRepository <T extends BaseMongoEntity> extends MongoRepository<T,Long> {
}
