package com.loserclub.pushdata.datacenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@ComponentScan({
        "com.loserclub.shared",
        "com.loserclub.pushdata.common",
        "com.loserclub.pushdata.datacenter"
})
@SpringBootApplication(scanBasePackages = {
        "com.loserclub.shared",
        "com.loserclub.pushdata.common",
        "com.loserclub.pushdata.datacenter",
},
        exclude = {
                RedisAutoConfiguration.class,
                RedisRepositoriesAutoConfiguration.class
        })
//@EnableFeignClients(basePackages = {
//        "com.loserclub.shared",
//        "com.loserclub.pushdata.common",
//        "com.loserclub.pushdata.datacenter"
//})
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class DatacenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatacenterApplication.class, args);
    }

}
