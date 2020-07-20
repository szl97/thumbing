package com.loserclub.pushdata.datacenter.config;

import com.loserclub.shared.config.SharedJpaConfig;
import com.loserclub.shared.jpa.GlobalFilterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Configuration
public class JpaConfig {
    @PersistenceContext
    private EntityManager em;
    @Bean
    public GlobalFilterHandler globalFilterHandler(){
        return SharedJpaConfig.getInstance(em).globalFilterHandler();
    }
}
