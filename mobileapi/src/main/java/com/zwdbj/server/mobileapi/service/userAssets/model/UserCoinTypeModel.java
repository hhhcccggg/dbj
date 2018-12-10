package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "分类型存储用户金币总额")
public class UserCoinTypeModel implements Serializable {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "TASK:任务;PAY:充值;INCOME:收益;OTHER:其他")
    String type;
    @ApiModelProperty(value = "金币总额")
    long coins;
    @ApiModelProperty(value = "userId")
    long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
