package com.zwdbj.server.config.settings;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 发送私信配置
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.letter")
public class LetterSendConfigs {
    private int sendMaxCount;

    public LetterSendConfigs(int sendMaxCount) {
        this.sendMaxCount = sendMaxCount;
    }

    public LetterSendConfigs() {
    }

    public int getSendMaxCount() {
        return sendMaxCount;
    }

    public void setSendMaxCount(int sendMaxCount) {
        this.sendMaxCount = sendMaxCount;
    }
}
