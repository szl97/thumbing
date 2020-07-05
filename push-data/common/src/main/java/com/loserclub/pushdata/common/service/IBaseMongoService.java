package com.loserclub.pushdata.common.service;


import com.loserclub.pushdata.common.entity.mongo.BaseMongoEntity;
import com.loserclub.pushdata.common.repository.IBaseMongoRepository;
/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public interface IBaseMongoService <T extends BaseMongoEntity, K extends IBaseMongoRepository<T>> {
}
