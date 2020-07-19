package com.loserclub.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({
        "com.loserclub.shared",
        "com.loserclub.auth",
})
@SpringBootApplication(scanBasePackages = {
        "com.loserclub.shared",
        "com.loserclub.auth",
        }
)
@EntityScan(basePackages = "com.loserclub.shared")
@EnableJpaRepositories(basePackages = "com.loserclub.shared")
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
