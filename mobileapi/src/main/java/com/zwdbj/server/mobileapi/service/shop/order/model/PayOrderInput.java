package com.zwdbj.server.mobileapi.service.shop.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "订单支付的输入字段")
public class PayOrderInput {
    @ApiModelProperty(value = "订单的id")
    private long orderId;
    @ApiModelProperty(value = "订单需要支付的总金额")
    private int payMoney;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(int payMoney) {
        this.payMoney = payMoney;
    }
}
