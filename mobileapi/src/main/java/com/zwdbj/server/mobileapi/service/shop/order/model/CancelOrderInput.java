package com.zwdbj.server.mobileapi.service.shop.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "取消订单的参数")
public class CancelOrderInput {
    @ApiModelProperty(value = "订单id")
    private long orderId;
    @ApiModelProperty(value = "订单取消的原因")
    private String cancelReason;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
