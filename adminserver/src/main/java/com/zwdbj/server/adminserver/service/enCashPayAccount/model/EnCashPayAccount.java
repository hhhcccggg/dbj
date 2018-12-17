package com.zwdbj.server.adminserver.service.enCashPayAccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "提现第三方支付账号")
public class EnCashPayAccount {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "账号类型")
    String type;

    @ApiModelProperty(value = "账号")
    String uniqueId;
    @ApiModelProperty(value = "名称")
    String name;
    @ApiModelProperty(value = "头像")
    String avatarUrl;

    @ApiModelProperty(value = "访问token")
    String accessToken;

    @ApiModelProperty(value = "过期时间")
    Long expireIn;

    @ApiModelProperty(value = "用户")
    Long userId;

    @ApiModelProperty(value = "是否锁定")
    boolean isLocked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
