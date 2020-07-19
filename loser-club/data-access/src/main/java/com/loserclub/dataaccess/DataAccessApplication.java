package com.loserclub.dataaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan({
        "com.loserclub.shared",
        "com.loserclub.dataaccess",
})
@SpringBootApplication(scanBasePackages = {
        "com.loserclub.shared",
        "com.loserclub.dataaccess",
}
)
@EntityScan(basePackages = "com.loserclub.shared")
@EnableJpaRepositories(basePackages = "com.loserclub.shared")
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class DataAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataAccessApplication.class, args);
    }

}
