package com.zwdbj.server.mobileapi.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "提现")
public class EnCashInput implements Serializable {
    @ApiModelProperty(value = "账号类型：ALIPAY:支付宝账号;WECHAT:微信账号,更多扩展")
    String payAccountType;
    @ApiModelProperty(value = "提现账号id")
    long payAccountId;
    @NotNull
    @Min(value = 500,message = "最小提现金额为5元")
    @ApiModelProperty(value = "提现的金额")
    int rmbs;

    public String getPayAccountType() {
        return payAccountType;
    }

    public void setPayAccountType(String payAccountType) {
        this.payAccountType = payAccountType;
    }


    public long getPayAccountId() {
        return payAccountId;
    }

    public void setPayAccountId(long payAccountId) {
        this.payAccountId = payAccountId;
    }

    public int getRmbs() {
        return rmbs;
    }

    public void setRmbs(int rmbs) {
        this.rmbs = rmbs;
    }
}
