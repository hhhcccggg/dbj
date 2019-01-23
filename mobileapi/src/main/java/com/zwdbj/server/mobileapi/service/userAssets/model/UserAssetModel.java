package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "用户资产")
public class UserAssetModel implements Serializable {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "userId")
    long userId;
    @ApiModelProperty(value = "用户小饼干")
    long coins;
    @ApiModelProperty(value = "用户余额")
    long remainBalance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(long remainBalance) {
        this.remainBalance = remainBalance;
    }
}
