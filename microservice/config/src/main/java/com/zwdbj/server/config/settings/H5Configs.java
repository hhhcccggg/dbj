package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.h5")
public class H5Configs {
    private String mobileUrl;

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getShopListUrl(long id,String type) {
        return mobileUrl+"/shop/#/userlist?resId="+id+"&resType="+type;
    }
    public String getLiveAddShopUrl(long id) {
        return mobileUrl+"/shop/#/home?resId="+id;
    }
    public String getShareUrl(long id,String type) {
        return mobileUrl+"/share/html/share.html?resId="+id+"&resType="+type;
    }
}
