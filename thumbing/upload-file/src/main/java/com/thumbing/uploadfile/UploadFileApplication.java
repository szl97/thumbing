package com.thumbing.uploadfile;

import com.thumbing.shared.controller.ThumbingBaseController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({
        "com.thumbing.shared",
        "com.thumbing.uploadfile",
})
@SpringBootApplication(scanBasePackages = {
        "com.thumbing.shared",
        "com.thumbing.uploadfile",
})
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class UploadFileApplication extends ThumbingBaseController {

    public static void main(String[] args) {
        SpringApplication.run(UploadFileApplication.class, args);
    }

}
