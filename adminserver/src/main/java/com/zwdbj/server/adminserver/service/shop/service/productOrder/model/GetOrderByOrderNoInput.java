package com.zwdbj.server.adminserver.service.shop.service.productOrder.model;

import io.swagger.annotations.ApiModelProperty;

public class GetOrderByOrderNoInput {
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
