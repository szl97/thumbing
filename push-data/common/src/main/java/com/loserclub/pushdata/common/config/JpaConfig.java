package com.loserclub.pushdata.common.config;

import com.loserclub.pushdata.common.jpa.GlobalFilterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */

@Configuration
public class JpaConfig {
    @PersistenceContext
    private EntityManager em;

    //初始化全局过滤器需要得数据 从实体中获取 根据注解来
    @Bean
    public GlobalFilterHandler globalFilterHandler(){
        GlobalFilterHandler globalFilterHandler=new GlobalFilterHandler(em.getEntityManagerFactory());
        globalFilterHandler.init();
        return globalFilterHandler;
    }
}
