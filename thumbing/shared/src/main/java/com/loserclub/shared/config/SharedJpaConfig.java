package com.loserclub.shared.config;

import com.loserclub.shared.jpa.GlobalFilterHandler;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@Data
public class SharedJpaConfig {
    private EntityManager em;

    private static SharedJpaConfig INSTANCE = new SharedJpaConfig();

    private static Object lock = new Object();

    private SharedJpaConfig(){

    }

    public static SharedJpaConfig getInstance(EntityManager em){
        if(INSTANCE.getEm() == null){
            synchronized (lock){
                if(INSTANCE.getEm() == null){
                    INSTANCE.setEm(em);
                }
            }
        }
        return INSTANCE;
    }

    //初始化全局过滤器需要得数据 从实体中获取 根据注解来
    public GlobalFilterHandler globalFilterHandler() {
        GlobalFilterHandler globalFilterHandler = new GlobalFilterHandler(em.getEntityManagerFactory());
        globalFilterHandler.init();
        return globalFilterHandler;
    }
}
