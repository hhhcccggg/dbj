package com.zwdbj.server.basearc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BasearcApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasearcApplication.class, args);
    }

}
