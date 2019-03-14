package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.xg")
public class XgConfigs {
    private String serviceUrl;
    private String androidAppid;
    private String androidSecrect;
    private String iosAppid;
    private String iosSecrect;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getAndroidAppid() {
        return androidAppid;
    }

    public void setAndroidAppid(String androidAppid) {
        this.androidAppid = androidAppid;
    }

    public String getAndroidSecrect() {
        return androidSecrect;
    }

    public void setAndroidSecrect(String androidSecrect) {
        this.androidSecrect = androidSecrect;
    }

    public String getIosAppid() {
        return iosAppid;
    }

    public void setIosAppid(String iosAppid) {
        this.iosAppid = iosAppid;
    }

    public String getIosSecrect() {
        return iosSecrect;
    }

    public void setIosSecrect(String iosSecrect) {
        this.iosSecrect = iosSecrect;
    }
}
