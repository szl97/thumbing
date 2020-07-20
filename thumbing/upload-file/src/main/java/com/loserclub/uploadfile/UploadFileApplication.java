package com.loserclub.uploadfile;

import com.loserclub.shared.controller.LoserClubBaseController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({
        "com.loserclub.shared",
        "com.loserclub.uploadfile",
})
@SpringBootApplication(scanBasePackages = {
        "com.loserclub.shared",
        "com.loserclub.uploadfile",
})
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class UploadFileApplication extends LoserClubBaseController {

    public static void main(String[] args) {
        SpringApplication.run(UploadFileApplication.class, args);
    }

}
