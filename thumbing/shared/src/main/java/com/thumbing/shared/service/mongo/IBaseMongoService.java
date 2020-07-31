package com.thumbing.shared.service.mongo;


import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public interface IBaseMongoService<T extends BaseMongoEntity, K extends IBaseMongoRepository<T>> {
}
