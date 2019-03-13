package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.easemob")
public class EasemobConfigs {
    private String orgName;
    private String appName;
    private String clientid;
    private String clientSecrect;
    private String httpbase;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientSecrect() {
        return clientSecrect;
    }

    public void setClientSecrect(String clientSecrect) {
        this.clientSecrect = clientSecrect;
    }

    public String getHttpbase() {
        return httpbase;
    }

    public void setHttpbase(String httpbase) {
        this.httpbase = httpbase;
    }
}
