package com.zwdbj.server.mobileapi.service.rankingList.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "排行榜")
public class RankingListInfo {
    @ApiModelProperty(value = "用户id")
    long userId;
    @ApiModelProperty(value = "用户名")
    String userName;
    @ApiModelProperty(value = "用户头像url")
    String avatarUrl;
    @ApiModelProperty(value = "累计获得的金币数")
    String totalCoins;
    @ApiModelProperty(value = "排名")
    long rank;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(String totalCoins) {
        this.totalCoins = totalCoins;
    }
}
