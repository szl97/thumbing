package com.loserclub.pushdata.nodeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@ComponentScan({
        "com.loserclub.shared",
        "com.loserclub.pushdata.common",
        "com.loserclub.pushdata.nodeserver"
})
@SpringBootApplication(scanBasePackages = {
        "com.loserclub.shared",
        "com.loserclub.pushdata.common",
        "com.loserclub.pushdata.nodeserver"
})
public class NodeserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NodeserverApplication.class, args);
    }

}
