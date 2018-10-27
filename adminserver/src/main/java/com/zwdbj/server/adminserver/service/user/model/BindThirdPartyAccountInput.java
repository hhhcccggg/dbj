package com.zwdbj.server.adminserver.service.user.model;


import io.swagger.annotations.ApiModelProperty;

/**
 * 第三方登录&绑定第三方账号
 */
public class BindThirdPartyAccountInput {
    @ApiModelProperty(value = "4:微信8:QQ16:微博，目前只能是这些值. 如果是修改某个绑定信息，后端会忽略此字段值")
    int thirdType;
    @ApiModelProperty(value = "第三方的用户的唯一标识ID")
    String openUserId;
    @ApiModelProperty(value = "昵称")
    String nickName;
    @ApiModelProperty(value = "头像")
    String avaterUrl;
    @ApiModelProperty(value = "性别:0:未知1:男2:女3:保密")
    int sex;
    @ApiModelProperty(value = "第三方登录返回的token")
    String accessToken;
    @ApiModelProperty(value = "accessToken的有效期，单位秒")
    long expireIn;

    public int getThirdType() {
        return thirdType;
    }

    public void setThirdType(int thirdType) {
        this.thirdType = thirdType;
    }




    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvaterUrl() {
        return avaterUrl;
    }

    public void setAvaterUrl(String avaterUrl) {
        this.avaterUrl = avaterUrl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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
