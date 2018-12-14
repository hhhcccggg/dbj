package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("用户搜索(Ad)")
public class UserSearchForAdInput {
    @ApiModelProperty("是否已经认证:-1>>所有；0：未认证1：已认证")
    int isReviewed;
    @ApiModelProperty("是否锁定：-1>>所有；0：未锁定1：已锁定")
    int isLocked;
    @ApiModelProperty("登录方式：-1>>所有；1：手机号2：账号4：微信8：qq16：微博")
    int loginType;
    @ApiModelProperty("0:所有")
    String keyWords;
    @ApiModelProperty("角色名称:market,admin,finance,normal null")
    String roleName;
    @ApiModelProperty("查询的开始时间")
    String startTime;
    @ApiModelProperty("查询的结束时间")
    String endTime;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(int isReviewed) {
        this.isReviewed = isReviewed;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
