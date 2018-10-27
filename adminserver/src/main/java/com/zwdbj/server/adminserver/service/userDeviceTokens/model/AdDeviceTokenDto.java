package com.zwdbj.server.adminserver.service.userDeviceTokens.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "查询设备类型和token等输出字段")
public class AdDeviceTokenDto {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "创建时间")
    Date createTime;
    @ApiModelProperty(value = "设备绑定的userId")
    Long userId;
    @ApiModelProperty(value = "设备的token")
    String deviceToken;
    @ApiModelProperty(value = "设备的类型")
    String deviceType;
    @ApiModelProperty(value = "设备的名字,可以为空")
    String deviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
