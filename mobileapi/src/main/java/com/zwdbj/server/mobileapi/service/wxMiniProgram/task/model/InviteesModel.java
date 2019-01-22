package com.zwdbj.server.mobileapi.service.wxMiniProgram.task.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "被邀请的人")
public class InviteesModel {
    @ApiModelProperty(value = "邀请的人的id")
    private long initiatorUserId;
    @ApiModelProperty(value = "被邀请的人的id")
    private long receivedUserId;
    @ApiModelProperty(value = "被邀请的人的昵称")
    private String nickName;
    @ApiModelProperty(value = "被邀请的人头像URL")
    private String avatarUrl;
    @ApiModelProperty(value = "邀请人获得的奖励")
    private int coins;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public long getReceivedUserId() {
        return receivedUserId;
    }

    public void setReceivedUserId(long receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}
