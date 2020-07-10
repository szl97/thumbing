package com.loserclub.shared.service;


import com.loserclub.shared.entity.mongo.BaseMongoEntity;
import com.loserclub.shared.repository.IBaseMongoRepository;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public interface IBaseMongoService<T extends BaseMongoEntity, K extends IBaseMongoRepository<T>> {
}
