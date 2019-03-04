package com.zwdbj.server.mobileapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zwdbj.server.serviceinterface.*")
@ComponentScan(basePackages = {"com.zwdbj.server.*"})
@MapperScan(basePackages = {"com.zwdbj.server.*"})
public class MobileAPIApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MobileAPIApplication.class);
        app.addListeners(new ApplicationStartedEventLister());
        ConfigurableApplicationContext applicationContext = app.run(args);
    }
}
