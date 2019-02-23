package com.zwdbj.server.mobileapi.service.shop.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "订单详情")
public class ProductOrderDetailModel extends ProductOrderModel {
    @ApiModelProperty(value = "支付类型，BANK:银行卡WECHAT:微信ALIPAY:支付宝NONE:无")
    private String paymentType;
    @ApiModelProperty(value = "第三方支付流水号: 微信、支付宝流水号、银行卡流水号 NONE:无")
    private String thirdPaymentTradeNo;
    @ApiModelProperty(value = "订单付款时间")
    private Date paymentTime;
    @ApiModelProperty(value = "订单发货时间")
    private Date deliveryTime;
    @ApiModelProperty(value = "交易更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "交易结束时间")
    private Date endTime;
    @ApiModelProperty(value = "交易关闭时间")
    private Date closeTime;
    @ApiModelProperty(value = "物流名字")
    private String deliveryName;
    @ApiModelProperty(value = "物流类型，主要是物流公司")
    private String deliveryType;
    @ApiModelProperty(value = "物流单号")
    private String deliveryCode;
    @ApiModelProperty(value = "买家留言")
    private String buyerComment;
    @ApiModelProperty(value = "买家是否已评价")
    private boolean buyerRate;
    @ApiModelProperty(value = "订单的最后支付时间")
    private Date lastPayTime;

    @ApiModelProperty(value = "店铺的id")
    private long storeId;
    @ApiModelProperty(value = "店铺的名字")
    private String name;

    public Date getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getThirdPaymentTradeNo() {
        return thirdPaymentTradeNo;
    }

    public void setThirdPaymentTradeNo(String thirdPaymentTradeNo) {
        this.thirdPaymentTradeNo = thirdPaymentTradeNo;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

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

    public String getBuyerComment() {
        return buyerComment;
    }

    public void setBuyerComment(String buyerComment) {
        this.buyerComment = buyerComment;
    }

    public boolean isBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
