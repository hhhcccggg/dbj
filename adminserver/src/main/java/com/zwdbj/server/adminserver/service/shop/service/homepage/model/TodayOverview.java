package com.zwdbj.server.adminserver.service.shop.service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "今日概览")
public class TodayOverview {
    @ApiModelProperty(value = "支付金额")
    float payments;
    @ApiModelProperty(value = "支付订单数")
    int productOrders;
    @ApiModelProperty(value = "代言人视频数")
    int superStarVideos;

    public float getPayments() {
        return payments;
    }

    public void setPayments(float payments) {
        this.payments = payments;
    }

    public int getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(int productOrders) {
        this.productOrders = productOrders;
    }

    public int getSuperStarVideos() {
        return superStarVideos;
    }

    public void setSuperStarVideos(int superStarVideos) {
        this.superStarVideos = superStarVideos;
    }
}
