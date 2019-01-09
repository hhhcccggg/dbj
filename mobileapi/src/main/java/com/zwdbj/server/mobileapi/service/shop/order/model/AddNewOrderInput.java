package com.zwdbj.server.mobileapi.service.shop.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "创建新订单所需字段")
public class AddNewOrderInput {
    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "使用的小饼干抵扣数")
    private int useCoin;
    @ApiModelProperty(value = "邮费，若为0则免邮费，单位为分")
    private int deliveryFee;


    @NotNull
    @ApiModelProperty(value = "是否限购,0：表示不限购  大于0数字表示每人只能买商品的数量")
    private int limitPerPerson;


    @ApiModelProperty(value = "买家留言")
    private String buyerComment;

    @NotNull
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    @NotNull
    @ApiModelProperty(value = "收货地址id")
    private long receiveAddressId;
    @NotNull
    @ApiModelProperty(value = "商品id")
    private long productId;
    @ApiModelProperty(value = "商品SKUid")
    private long productskuId;


    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "购买的商品数量")
    private int num;
    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "支付的实际金额")
    private int actualPayment;
    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "优惠的金额")
    private int coupon;

    @NotNull
    @Min(value = 1)
    @ApiModelProperty(value = "购买的商品单价")
    private int price;
    @NotNull(message = "商品标题不能为空")
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "优惠券id")
    private String couponids;

    public int getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(int actualPayment) {
        this.actualPayment = actualPayment;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public int getUseCoin() {
        return useCoin;
    }

    public void setUseCoin(int useCoin) {
        this.useCoin = useCoin;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getLimitPerPerson() {
        return limitPerPerson;
    }

    public void setLimitPerPerson(int limitPerPerson) {
        this.limitPerPerson = limitPerPerson;
    }

    public String getBuyerComment() {
        return buyerComment;
    }

    public void setBuyerComment(String buyerComment) {
        this.buyerComment = buyerComment;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(long receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductskuId() {
        return productskuId;
    }

    public void setProductskuId(long productskuId) {
        this.productskuId = productskuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCouponids() {
        return couponids;
    }

    public void setCouponids(String couponids) {
        this.couponids = couponids;
    }
}
