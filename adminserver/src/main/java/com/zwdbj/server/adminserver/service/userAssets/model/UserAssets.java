package com.zwdbj.server.adminserver.service.userAssets.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户资产")
public class UserAssets {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "用户金币")
    Long coins;

    @ApiModelProperty(value = "用户余额")
    Long remainBalance;

    @ApiModelProperty(value = "用户id")
    Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public Long getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(Long remainBalance) {
        this.remainBalance = remainBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
