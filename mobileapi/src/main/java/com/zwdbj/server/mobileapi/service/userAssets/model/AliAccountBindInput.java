package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AliAccountBindInput implements Serializable {
    /**
     * 支付宝登录后返回的auth_code
     */
    @ApiModelProperty("支付宝登录后返回的auth_code")
    private String authCode;
    /**
     * 支付宝登录用户id
     */
    @ApiModelProperty("支付宝登录用户id")
    private String userId;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
