package com.zwdbj.server.mobileapi.service.pay.wechat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("金币充值")
public class ChargeCoinInput implements Serializable {
    @ApiModelProperty("金币充值方案，当自定义选择充值金额时，请直接传0")
    private long planId;
    @ApiModelProperty("待充值的金币数.planId和coins字段同时有值，如果planId>0,则优先选择planId")
    private int coins;

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
