//package com.thumbing.shared.config;
//
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
///**
//  * @Author: Stan Sai
//  * @Date: 2020/8/22 16:45
// */
//public class JacksonMapperConfig {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {
//
//            @Override
//            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
//
//                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
//                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
//
//            }
//        };
//
//        return cunstomizer;
//    }
//}
