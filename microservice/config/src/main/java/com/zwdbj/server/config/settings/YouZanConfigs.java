package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.youzan")
public class YouZanConfigs {
    private String clientid;
    private String secrect;
    private String bindShopId;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getSecrect() {
        return secrect;
    }

    public void setSecrect(String secrect) {
        this.secrect = secrect;
    }

    public String getBindShopId() {
        return bindShopId;
    }

    public void setBindShopId(String bindShopId) {
        this.bindShopId = bindShopId;
    }
}
