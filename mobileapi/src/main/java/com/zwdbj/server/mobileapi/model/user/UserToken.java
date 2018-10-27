package com.zwdbj.server.mobileapi.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "用户Token")
public class UserToken implements Serializable {

    /**
     * 访问Token
     */
    @ApiModelProperty(value = "访问Token")
    private String accessToken;
    /**
     * 过期时间，单位秒
     */
    @ApiModelProperty(value = "过期时间，单位秒")
    private long expireTime;


    public UserToken(){

    }

    public UserToken(String accessToken, long expireTime) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
    }

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
