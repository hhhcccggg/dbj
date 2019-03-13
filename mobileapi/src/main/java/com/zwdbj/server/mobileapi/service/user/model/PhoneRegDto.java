package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModelProperty;

public class PhoneRegDto {
    @ApiModelProperty(value = "此手机号是否被注册，100:未被注册，201:已经注册过但是没有设置密码,202:已经注册过并设置密码")
    private int type;
    @ApiModelProperty(value = "秘钥")
    private String key;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
