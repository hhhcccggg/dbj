package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.push")
public class PushConfigs {
    /**
     * dev:测试开发环境
     * production:生产环境
     */
    private String pushEnvironment;

    public String getPushEnvironment() {
        return pushEnvironment;
    }

    public void setPushEnvironment(String pushEnvironment) {
        this.pushEnvironment = pushEnvironment;
    }
}

