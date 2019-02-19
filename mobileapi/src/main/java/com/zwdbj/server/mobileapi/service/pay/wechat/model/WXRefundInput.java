package com.zwdbj.server.mobileapi.service.pay.wechat.model;

import io.swagger.annotations.ApiModelProperty;

public class WXRefundInput {
    /**
     * 微信订单号
     */
    @ApiModelProperty("订单号")
    private String transactionId;

    @ApiModelProperty(value = "订单id")
    private long orderId;
    @ApiModelProperty("订单金额")
    private int totalFee;
    @ApiModelProperty("退款金额")
    private int refundFee;
    @ApiModelProperty("退款原因")
    public  String refundDesc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }
}
