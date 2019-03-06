package com.zwdbj.server.common.sms;

import java.io.Serializable;

public class SmsSendCfg implements Serializable {
    // 上次发送验证码的时间戳
    private long lastSendSmsTimeStamp;
    // 每天发送验证码的上限，格式:2018-08-15:4
    private String daySendCount;

    public SmsSendCfg(long lastSendSmsTimeStamp, String daySendCount) {
        this.lastSendSmsTimeStamp = lastSendSmsTimeStamp;
        this.daySendCount = daySendCount;
    }

    public long getLastSendSmsTimeStamp() {
        return lastSendSmsTimeStamp;
    }

    public void setLastSendSmsTimeStamp(long lastSendSmsTimeStamp) {
        this.lastSendSmsTimeStamp = lastSendSmsTimeStamp;
    }

    public String getDaySendCount() {
        return daySendCount;
    }

    public void setDaySendCount(String daySendCount) {
        this.daySendCount = daySendCount;
    }
}
