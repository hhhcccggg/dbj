package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModelProperty;

public class AdLivingInfoDto extends LivingInfoDto {
    @ApiModelProperty(value = "直播收入")
    double income;
    @ApiModelProperty(value = "直播的是否违规状态, 0:正常 1:人工审核 2:违规")
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
