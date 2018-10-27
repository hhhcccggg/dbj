package com.zwdbj.server.adminserver.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(description = "APP相关的配置信息")
public class AppConfigDto {
    @ApiModelProperty(value = "当前支持的角色列表")
    List<String> roles;
    @ApiModelProperty(value = "性别")
    Map<Integer,String> sexs;
    @ApiModelProperty(value = "登录类别")
    Map<Integer,String> loginType;
    @ApiModelProperty(value = "app是否开启直播功能(包括直播和观看)")
    boolean isAppLivingOpen;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<Integer, String> getSexs() {
        return sexs;
    }

    public void setSexs(Map<Integer, String> sexs) {
        this.sexs = sexs;
    }

    public Map<Integer, String> getLoginType() {
        return loginType;
    }

    public void setLoginType(Map<Integer, String> loginType) {
        this.loginType = loginType;
    }

    public boolean isAppLivingOpen() {
        return isAppLivingOpen;
    }

    public void setAppLivingOpen(boolean appLivingOpen) {
        isAppLivingOpen = appLivingOpen;
    }
}
