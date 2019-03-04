package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 发送短信&验证码配置
 */
@Component
@ConfigurationProperties(prefix = "app.sms")
public class SmsSendConfigs {
    private int sendInterval;
    private int sendMaxCountDay;
    private int codeExpireTime;
    private boolean sendOpen;

    public int getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(int sendInterval) {
        this.sendInterval = sendInterval;
    }

    public int getSendMaxCountDay() {
        return sendMaxCountDay;
    }

    public void setSendMaxCountDay(int sendMaxCountDay) {
        this.sendMaxCountDay = sendMaxCountDay;
    }

    public int getCodeExpireTime() {
        return codeExpireTime;
    }

    public void setCodeExpireTime(int codeExpireTime) {
        this.codeExpireTime = codeExpireTime;
    }

    public boolean isSendOpen() {
        return sendOpen;
    }

    public void setSendOpen(boolean sendOpen) {
        this.sendOpen = sendOpen;
    }
}
