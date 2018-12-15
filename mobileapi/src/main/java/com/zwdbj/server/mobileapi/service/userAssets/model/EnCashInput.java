package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "提现")
public class EnCashInput implements Serializable {
    @ApiModelProperty(value = "账号类型：ALIPAY:支付宝账号;WECHAT:微信账号,更多扩展")
    String payAccountType;
    @ApiModelProperty(value = "账号")
    String payAccountId;
    @ApiModelProperty(value = "提现的金额")
    int rmbs;

    public String getPayAccountType() {
        return payAccountType;
    }

    public void setPayAccountType(String payAccountType) {
        this.payAccountType = payAccountType;
    }

    public String getPayAccountId() {
        return payAccountId;
    }

    public void setPayAccountId(String payAccountId) {
        this.payAccountId = payAccountId;
    }

    public int getRmbs() {
        return rmbs;
    }

    public void setRmbs(int rmbs) {
        this.rmbs = rmbs;
    }
}
