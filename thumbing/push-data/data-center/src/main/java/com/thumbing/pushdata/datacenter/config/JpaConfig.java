package com.thumbing.pushdata.datacenter.config;

import com.thumbing.shared.config.SharedJpaConfig;
import com.thumbing.shared.jpa.GlobalFilterHandler;
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
