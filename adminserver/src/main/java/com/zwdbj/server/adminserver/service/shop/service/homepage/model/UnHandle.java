package com.zwdbj.server.adminserver.service.shop.service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "未处理")

public class UnHandle {
    @ApiModelProperty(value = "未使用订单")
    private int orders;
    @ApiModelProperty(value = "未恢复评价")
    private int comments;

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
