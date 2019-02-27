package com.zwdbj.server.basearc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zwdbj.server.basearc.*","com.zwdbj.server.config.*"})
public class BasearcApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasearcApplication.class, args);
    }

}
