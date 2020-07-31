package com.thumbing.dataaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan({
        "com.thumbing.shared",
        "com.thumbing.dataaccess",
})
@SpringBootApplication(scanBasePackages = {
        "com.thumbing.shared",
        "com.thumbing.dataaccess",
}
)
@EntityScan(basePackages = "com.thumbing.shared")
@EnableJpaRepositories(basePackages = "com.thumbing.shared")
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class DataAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataAccessApplication.class, args);
    }

}
