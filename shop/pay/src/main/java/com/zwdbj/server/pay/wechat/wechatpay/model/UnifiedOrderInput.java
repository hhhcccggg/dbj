package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("微信支付统一下单")
public class UnifiedOrderInput implements Serializable {
    private static final long serialVersionUID = 3698491567492103384L;
    @ApiModelProperty("商品描述")
    private String body;
    @ApiModelProperty("订单号")
    private String outTradeNo;
    @ApiModelProperty("货币类型，人民币请传CNY,忽略其他类型")
    private String feeType;
    @ApiModelProperty("总金额，单位：分")
    private int totalFee;
    @ApiModelProperty("支付回调地址")
    private String notifyUrl;
    @ApiModelProperty("交易类型，默认APP")
    private String tradeType;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
