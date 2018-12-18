package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "提现，绑定第三方支付平台需要传入的字段")
public class BandingThirdInput implements Serializable {
    @ApiModelProperty(value = "账号类型：ALIPAY:支付宝账号;WECHAT:微信账号,更多扩展")
    String type;
    @ApiModelProperty(value = "账号")
    String uniqueId;
    @ApiModelProperty(value = "名称")
    String name;
    @ApiModelProperty(value = "头像")
    String avatarUrl;
    @ApiModelProperty(value = "访问token，可以根据token获取用户详情")
    String accessToken;
    @ApiModelProperty(value = "过期时间")
    long expireIn;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }
}
