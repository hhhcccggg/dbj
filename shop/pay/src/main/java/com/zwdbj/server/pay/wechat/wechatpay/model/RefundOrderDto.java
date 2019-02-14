package com.zwdbj.server.pay.wechat.wechatpay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "微信申请退款时返回的结果")
public class RefundOrderDto {

    @ApiModelProperty(value = "微信退款单号")
    private String refundId;
    @ApiModelProperty(value = "商户退款单号")
    private String outRefundNo;
    @ApiModelProperty(value = "微信订单单号")
    private String transactionId;
    @ApiModelProperty(value = "商户订单单号")
    private String outTradeNo;
    @ApiModelProperty("订单金额")
    private int totalFee;
    @ApiModelProperty("退款金额")
    private int refundFee;

    @ApiModelProperty(value = "现金支付金额")
    private int cashFee;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(int refundFee) {
        this.refundFee = refundFee;
    }

    public int getCashFee() {
        return cashFee;
    }

    public void setCashFee(int cashFee) {
        this.cashFee = cashFee;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }
}
