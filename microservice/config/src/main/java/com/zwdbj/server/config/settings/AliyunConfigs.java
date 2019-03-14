package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 阿里云基础配置信息
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.aliyun")
public class AliyunConfigs {
    private String accessKey;
    private String accessSecrect;
    private String smsCodeSignName;
    private String smsTemplateCode;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecrect() {
        return accessSecrect;
    }

    public void setAccessSecrect(String accessSecrect) {
        this.accessSecrect = accessSecrect;
    }

    public String getSmsCodeSignName() {
        return smsCodeSignName;
    }

    public void setSmsCodeSignName(String smsCodeSignName) {
        this.smsCodeSignName = smsCodeSignName;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }
}
