package com.thumbing.pushdata.datacenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@ComponentScan({
        "com.thumbing.shared",
        "com.thumbing.pushdata.common",
        "com.thumbing.pushdata.datacenter"
})
@SpringBootApplication(scanBasePackages = {
        "com.thumbing.shared",
        "com.thumbing.pushdata.common",
        "com.thumbing.pushdata.datacenter",
}
)
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class DataCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCenterApplication.class, args);
    }

}
