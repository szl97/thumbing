package com.loserclub.pushdata.datacenter.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.loserclub.pushdata.common.utils.redis.RedisUtilsForCollection;
import com.loserclub.pushdata.common.utils.redis.RedisUtilsForObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 配置redis工具类
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisUtilsForObject redisUtilsForObject(@Qualifier("redisTemplateForObject") RedisTemplate<String, Serializable> redisTemplate){
        return RedisUtilsForObject.getInstance(redisTemplate);
    }

    /**
     * 配置redis工具类
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisUtilsForCollection redisUtilsForCollection(@Qualifier("redisTemplateForCollection") RedisTemplate<String, Serializable> redisTemplate){
        return RedisUtilsForCollection.getInstance(redisTemplate);
    }
    /**
     * 配置存储 value 和 hash 类型的数据源的RedisTemplate
     * 存储 value 和 hash 类型的数据源是默认数据源 @Primary
     *
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplateForObject")
    @Primary
    public RedisTemplate<String, Serializable> redisTemplate(@Qualifier("redisFactoryForObject") RedisConnectionFactory factory) {
        return getRedisTemplate(factory);
    }

    /**
     * 配置存储 list、set、sorted set 类型的数据源的RedisTemplate
     *
     * @param factory2
     * @return
     */
    @Bean(name = "redisTemplateForCollection")
    public RedisTemplate<String, Serializable> redisTemplate2(@Qualifier("redisFactoryForCollection") RedisConnectionFactory factory2) {
        return getRedisTemplate(factory2);
    }

    /**
     * 配置存储 value 和 hash 类型的数据源的RedisConnectionFactory
     * @param redisPool 使用第一个redis的配置
     * @param redisConfig 使用第一个redis的配置
     * @return
     */
    @Bean(name = "redisFactoryForObject", destroyMethod = "destroy")
    @Primary
    public LettuceConnectionFactory redisConnectionFactoryForObject(@Qualifier("redisPoolForObject") GenericObjectPoolConfig redisPool,
                                                         @Qualifier("redisConfigForObject") RedisClusterConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfiguration);
        return factory;
    }

    /**
     * 配置存储 list、set、sorted set 类型的数据源的RedisConnectionFactory
     * @param redisPool  使用第二个redis的配置
     * @param redisConfig 使用第二个redis的配置
     * @return
     */
    @Bean(name = "redisFactoryForCollection", destroyMethod = "destroy")
    public LettuceConnectionFactory redisConnectionFactoryForCollection(@Qualifier("redisPoolForCollection") GenericObjectPoolConfig redisPool,
                                                           @Qualifier("redisConfigForCollection") RedisClusterConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfig, clientConfiguration);
        return factory;
    }

    /**
     * 第一个redis的配置
     * 用来存储 value 和 hash
     */
    @Data
    @Configuration
    private static class redisConf {
        @Value("${spring.redis.cluster.nodes}")
        private String nodes;
        @Value("${spring.redis.cluster.max-redirects}")
        private Integer maxRedirects;
        @Value("${spring.redis.password}")
        private String password;
        @Value("${spring.redis.database}")
        private Integer database;

        @Value("${spring.redis.lettuce.pool.max-active}")
        private Integer maxActive;
        @Value("${spring.redis.lettuce.pool.max-idle}")
        private Integer maxIdle;
        @Value("${spring.redis.lettuce.pool.max-wait}")
        private Long maxWait;
        @Value("${spring.redis.lettuce.pool.min-idle}")
        private Integer minIdle;

        @Bean(name = "redisPoolForObject")
        public GenericObjectPoolConfig redisPool() {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMaxWaitMillis(maxWait);
            poolConfig.setMaxTotal(maxActive);
            poolConfig.setMinIdle(minIdle);
            return poolConfig;
        }

        @Bean(name = "redisConfigForObject")
        public RedisClusterConfiguration jedisConfig() {
            RedisClusterConfiguration config = new RedisClusterConfiguration();

            String[] sub = nodes.split(",");
            List<RedisNode> nodeList = new ArrayList<>(sub.length);
            String[] tmp;
            for (String s : sub) {
                tmp = s.split(":");
                // fixme 先不考虑异常配置的case
                nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
            }

            config.setClusterNodes(nodeList);
            config.setMaxRedirects(maxRedirects);
            if(StringUtils.isNotBlank(password)) {
                config.setPassword(RedisPassword.of(password));
            }
            return config;
        }
    }

    /**
     * 第二个redis的配置
     * 用来存储 list、set、sorted set
     */
    @Data
    @Configuration
    private static class redisConf2 {
        @Value("${spring.redis2.cluster.nodes}")
        private String nodes;
        @Value("${spring.redis2.cluster.max-redirects}")
        private Integer maxRedirects;
        @Value("${spring.redis2.password}")
        private String password;
        @Value("${spring.redis2.database}")
        private Integer database;

        @Value("${spring.redis2.lettuce.pool.max-active}")
        private Integer maxActive;
        @Value("${spring.redis2.lettuce.pool.max-idle}")
        private Integer maxIdle;
        @Value("${spring.redis2.lettuce.pool.max-wait}")
        private Long maxWait;
        @Value("${spring.redis2.lettuce.pool.min-idle}")
        private Integer minIdle;

        @Bean(name = "redisPoolForCollection")
        public GenericObjectPoolConfig redisPool() {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMaxWaitMillis(maxWait);
            poolConfig.setMaxTotal(maxActive);
            poolConfig.setMinIdle(minIdle);
            return poolConfig;
        }

        @Bean(name = "redisConfigForCollection")
        public RedisClusterConfiguration redisConfig() {
            RedisClusterConfiguration config = new RedisClusterConfiguration();

            String[] sub = nodes.split(",");
            List<RedisNode> nodeList = new ArrayList<>(sub.length);
            String[] tmp;
            for (String s : sub) {
                tmp = s.split(":");
                // fixme 先不考虑异常配置的case
                nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
            }

            config.setClusterNodes(nodeList);
            config.setMaxRedirects(maxRedirects);
            if(StringUtils.isNotBlank(password)) {
                config.setPassword(RedisPassword.of(password));
            }
            return config;
        }
    }

    /**
     * 设置序列化方式
     *
     * @param factory
     * @return
     */

    private RedisTemplate<String, Serializable> getRedisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Serializable> template = new RedisTemplate();

        template.setConnectionFactory(factory);

        ObjectMapper objectMapper = new ObjectMapper();
        //反序列化时候遇到不匹配的属性并不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //序列化时候遇到空对象不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化的时候如果是无效子类型,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        //不使用默认的dateTime进行序列化,
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        //使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        objectMapper.registerModule(new JavaTimeModule());
        //启用反序列化所需的类型信息,在属性中添加@class
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        //配置null值的序列化器
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);

        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        template.setDefaultSerializer(redisSerializer);
        template.setValueSerializer(redisSerializer);
        template.setHashValueSerializer(redisSerializer);
        template.setKeySerializer(StringRedisSerializer.UTF_8);
        template.setHashKeySerializer(StringRedisSerializer.UTF_8);
        template.afterPropertiesSet();

        return template;
    }
}
