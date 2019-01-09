package com.zwdbj.server.adminserver.service.shop.service.productOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "订单需要的物流信息")
public class DeliverOrderInput {
    @ApiModelProperty(value = "物流名字")
    private String deliveryName;
    @ApiModelProperty(value = "物流类型，主要是物流公司")
    private String deliveryType;
    @ApiModelProperty(value = "物流单号")
    private String deliveryCode;

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }
}
