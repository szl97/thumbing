package com.loserclub.pushdata.datacenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@ComponentScan("com.loserclub.pushdata.common")
@SpringBootApplication(scanBasePackages = {
        "com.loserclub.pushdata.common",
})
@EnableFeignClients(basePackages = {
        "com.loserclub.pushdata.common"
})
public class DatacenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatacenterApplication.class, args);
    }

}
