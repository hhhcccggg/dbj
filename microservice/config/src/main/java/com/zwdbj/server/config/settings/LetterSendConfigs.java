package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "letter.pay")
public class LetterSendConfigs {
    private int sendMaxCount;

    public int getSendMaxCount() {
        return sendMaxCount;
    }

    public void setSendMaxCount(int sendMaxCount) {
        this.sendMaxCount = sendMaxCount;
    }
}
