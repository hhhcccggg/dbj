package com.zwdbj.server.shop_admin_service.service.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "订单趋势")
public class OrderTrend {
    @ApiModelProperty(value = "订单数")
    int counts;
    @ApiModelProperty(value = "日期")
    Date createTime;

    public int getCounts() {
        return counts;
    }

    public void setCount(int counts) {
        this.counts = counts;
    }

    public Date getDate() {
        return createTime;
    }

    public void setDate(Date date) {
        this.createTime = date;
    }
}
