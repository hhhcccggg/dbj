package com.zwdbj.server.tokencenter;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TokenInfoModel implements Serializable {
    /**
     * 访问TOKEN
     */
    @ApiModelProperty("访问TOKEN")
    private String accessToken;
    /**
     * 过期时间，单位秒
     */
    @ApiModelProperty("过期时间，单位秒")
    private long expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
