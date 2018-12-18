package com.zwdbj.server.adminserver.service.userAssets.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description= "提现详情")
public class EnCashMentDetailModel implements Serializable {
    @ApiModelProperty(value = "id")
    long id ;
    @ApiModelProperty(value = "userId")
    long userId;
    @ApiModelProperty(value = "createTime")
    Date createTime;
    @ApiModelProperty(value = "用户的昵称")
    String nickName;
    @ApiModelProperty(value = "提现的金币数量")
    int coins;
    @ApiModelProperty(value = "提现的金额")
    int rmbs;
    @ApiModelProperty(value = "第三方支付的用户id")
    long payAccountId;
    @ApiModelProperty(value = "第三方支付的类型")
    String payAccountType;
    @ApiModelProperty(value = "提现的状态")
    String status;
    @ApiModelProperty(value = "提现的结果原因")
    String resultReason;
    @ApiModelProperty(value = "提现的额外数据")
    String extraData;
    @ApiModelProperty(value = "提现的流水号")
    String tradeNo;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getRmbs() {
        return rmbs;
    }

    public void setRmbs(int rmbs) {
        this.rmbs = rmbs;
    }

    public long getPayAccountId() {
        return payAccountId;
    }

    public void setPayAccountId(long payAccountId) {
        this.payAccountId = payAccountId;
    }

    public String getPayAccountType() {
        return payAccountType;
    }

    public void setPayAccountType(String payAccountType) {
        this.payAccountType = payAccountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultReason() {
        return resultReason;
    }

    public void setResultReason(String resultReason) {
        this.resultReason = resultReason;
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
}
