package com.thumbing.shared.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DozerBeanMapperConfigure {

    @Autowired(required = false)
    private List<BeanMappingBuilder> beanMappingBuilders;

    @Bean
    public Mapper dozerMapper(){
        Mapper mapper = DozerBeanMapperBuilder.create()
                .withMappingBuilders(beanMappingBuilders)
                .build();
        return mapper;
    }
}
