package com.zwdbj.server.mobileapi.service.userDeviceTokens.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "设备推送绑定/解绑用户输入字段")
public class UserDeviceTokensInput {
    @ApiModelProperty(value = "绑定的用户id")
    Long userId;
    @ApiModelProperty(value = "设备的token")
    String deviceToken;
    @ApiModelProperty(value = "设备的类型:ios或者android,目前这两种取值")
    String deviceType;
    @ApiModelProperty(value = "设备的名字,可以为空")
    String deviceName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
