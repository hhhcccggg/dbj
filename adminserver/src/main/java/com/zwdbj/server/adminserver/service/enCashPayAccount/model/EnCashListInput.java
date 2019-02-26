package com.zwdbj.server.adminserver.service.enCashPayAccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "提现列表的参数")
public class EnCashListInput {
    @ApiModelProperty(value = "提现的状态,0:全部,1:审核中,2:审核成功,3:提现成功,4:提现失败")
    private int status;
    @ApiModelProperty(value = "提现申请的开头时间")
    private Date enCashStartTime;
    @ApiModelProperty(value = "提现申请的结尾时间")
    private Date enCashEndTime;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getEnCashStartTime() {
        return enCashStartTime;
    }

    public void setEnCashStartTime(Date enCashStartTime) {
        this.enCashStartTime = enCashStartTime;
    }

    public Date getEnCashEndTime() {
        return enCashEndTime;
    }

    public void setEnCashEndTime(Date enCashEndTime) {
        this.enCashEndTime = enCashEndTime;
    }
}
