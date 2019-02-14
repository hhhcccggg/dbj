package com.zwdbj.server.mobileapi.service.pay.alipay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "支付宝申请退款的请求参数")
public class AliRefundInput {
    @ApiModelProperty(value = "订单id")
    private long orderId;
    @ApiModelProperty(value = "订单详情id")
    private long orderItemId;
    @ApiModelProperty(value = "支付宝交易号，和商户订单号不能同时为空。")
    private String tradeNo;
    @ApiModelProperty("退款金额，单位分")
    private int refundAmount;
    @ApiModelProperty("退款的原因说明")
    private String refundReason;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
}
