package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户举报输入字段")
public class AdUserComplainInput {
    @ApiModelProperty(value = "用户认证状态,-1:全部,0:未认证,1:认证")
    int isReviewed;
    @ApiModelProperty(value = "用户登录方式,-1:全部,1:手机号2:账号和密码4:微信8:QQ16:微博")
    int loginType;
    @ApiModelProperty(value = "搜索的用户ID")
    String userId;

    public int getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(int isReviewed) {
        this.isReviewed = isReviewed;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
