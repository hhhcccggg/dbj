package com.zwdbj.server.mobileapi.service.pay.wechat.model;

import io.swagger.annotations.ApiModelProperty;

public class WXRefundInput {

    @ApiModelProperty(value = "订单id")
    private long orderId;
    @ApiModelProperty("退款原因")
    public  String refundDesc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }



    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }
}
