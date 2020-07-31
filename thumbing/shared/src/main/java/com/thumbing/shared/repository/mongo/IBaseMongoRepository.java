package com.thumbing.shared.repository.mongo;

import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IBaseMongoRepository<T extends BaseMongoEntity> extends MongoRepository<T, Long> {
}
