package com.zwdbj.server.adminserver.service.enCashPayAccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "我的提现列表输出字段")
public class EnCashListDto {
    @ApiModelProperty(value = "提现的申请时间")
    private Date createTime;
    @ApiModelProperty(value = "提现的金额")
    private int  rmbs;
    @ApiModelProperty(value = "提现的账户")
    private String  uniqueId;
    @ApiModelProperty(value = "提现的账户类型 ALIPAY:支付宝;WECHAT:微信")
    private String  type;
    @ApiModelProperty(value = "提现状态REVIEWING：审核中;SUCCESS：成功；REVIEWED：审核成功；FAILED：失败")
    private String  status;
    @ApiModelProperty(value = "提现的附加信息")
    private String  extraData;
    @ApiModelProperty(value = "提现的交易流水号")
    private String  tradeNo;
    @ApiModelProperty(value = "账号类型：PAY：第三方支付账号")
    private String  payAccountType;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getRmbs() {
        return rmbs;
    }

    public void setRmbs(int rmbs) {
        this.rmbs = rmbs;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPayAccountType() {
        return payAccountType;
    }

    public void setPayAccountType(String payAccountType) {
        this.payAccountType = payAccountType;
    }
}
