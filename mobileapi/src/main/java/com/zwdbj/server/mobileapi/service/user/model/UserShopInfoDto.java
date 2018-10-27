package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserShopInfoDto implements Serializable {
    @ApiModelProperty(value = "优惠券数量")
    private int lotteryTicketCount;
    @ApiModelProperty(value = "优惠券H5网页")
    private String lotteryUrl;
    @ApiModelProperty(value = "购物车数量")
    int cartNum;
    @ApiModelProperty(value = "购物车H5网页")
    String cartUrl;
    @ApiModelProperty(value = "订单数量，该字段当前不可用")
    int orderNum;
    @ApiModelProperty(value = "我的订单H5网页")
    String orderUrl;

    public int getLotteryTicketCount() {
        return lotteryTicketCount;
    }

    public void setLotteryTicketCount(int lotteryTicketCount) {
        this.lotteryTicketCount = lotteryTicketCount;
    }

    public String getLotteryUrl() {
        return lotteryUrl;
    }

    public void setLotteryUrl(String lotteryUrl) {
        this.lotteryUrl = lotteryUrl;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public String getCartUrl() {
        return cartUrl;
    }

    public void setCartUrl(String cartUrl) {
        this.cartUrl = cartUrl;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

}
