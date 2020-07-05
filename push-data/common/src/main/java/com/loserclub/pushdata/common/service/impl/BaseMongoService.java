package com.loserclub.pushdata.common.service.impl;

import com.loserclub.pushdata.common.entity.mongo.BaseMongoEntity;
import com.loserclub.pushdata.common.repository.IBaseMongoRepository;
import com.loserclub.pushdata.common.service.IBaseMongoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public class BaseMongoService<T extends BaseMongoEntity, K extends IBaseMongoRepository<T>> implements IBaseMongoService<T,K> {
    @Autowired
    K repository;
}
