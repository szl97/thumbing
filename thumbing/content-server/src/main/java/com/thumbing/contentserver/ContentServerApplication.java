package com.thumbing.contentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan({
        "com.thumbing.shared",
        "com.thumbing.contentserver",
})
@SpringBootApplication(scanBasePackages = {
        "com.thumbing.shared",
        "com.thumbing.contentserver",
}
)
@EnableMongoRepositories(basePackages = "com.thumbing.shared")
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class ContentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServerApplication.class, args);
    }

}
