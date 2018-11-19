package com.zwdbj.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = {"com.zwdbj.server.*"})
public class AdminOperateApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AdminOperateApplication.class);
        app.addListeners(new ApplicationStartedEventLister());
        ConfigurableApplicationContext applicationContext = app.run(args);
    }
}
