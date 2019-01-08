package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "优惠，代金券详情")
public class DiscountCouponDetail extends DiscountCoupon {

    @ApiModelProperty(value = "产品说明")
    String userInfo;
    @ApiModelProperty(value = "有效期")
    Date validStartTime;
    @ApiModelProperty(value = "有效期")
    Date validEndTime;
    @ApiModelProperty(value = "预约信息")
    String order;
    @ApiModelProperty(value = "规则提醒")
    String rule;
    boolean onlySupportOriginProduct;
    @ApiModelProperty(value = "适用范围")
    String range;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isOnlySupportOriginProduct() {
        return onlySupportOriginProduct;
    }

    public void setOnlySupportOriginProduct(boolean onlySupportOriginProduct) {
        this.onlySupportOriginProduct = onlySupportOriginProduct;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
