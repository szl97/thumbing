package com.thumbing.pushdata.nodeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@ComponentScan(basePackages = {
        "com.thumbing.shared",
        "com.thumbing.pushdata.common",
        "com.thumbing.pushdata.nodeserver"
}
)
@SpringBootApplication(scanBasePackages = {
        "com.thumbing.shared",
        "com.thumbing.pushdata.common",
        "com.thumbing.pushdata.nodeserver"
})
public class NodeserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NodeserverApplication.class, args);
    }

}
