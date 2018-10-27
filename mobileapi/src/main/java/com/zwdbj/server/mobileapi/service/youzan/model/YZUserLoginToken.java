package com.zwdbj.server.mobileapi.service.youzan.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(description = "有赞移动端用户自动登录Token信息")
public class YZUserLoginToken implements Serializable {
    String cookieValue;
    String cookieKey;
    String accessToken;

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
