package com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "我的兑换")
public class OrderOut {

    @ApiModelProperty(value = "订单编号")
    private long orderNo;

    @ApiModelProperty(value = "商品ID")
    private long productId;

    @ApiModelProperty(value = "商品productskuId")
    private long productskuId;

    @ApiModelProperty(value = "storeId")
    private long storeId;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "单价")
    private int price;

    @ApiModelProperty(value = "订单总金额")
    private int payment;

    @ApiModelProperty(value = "实付金额")
    private int actualPayment;

    @ApiModelProperty(value = "产品图")
    private String imageUrls;

    @ApiModelProperty(value = "订单状态  交易状态 STATE_WAIT_BUYER_PAY（交易创建，等待买家付款）STATE_BUYER_PAYED (买家已付款) STATE_SELLER_DELIVERIED（卖家已发货，等待买家收货) STATE_BUYER_DELIVERIED（买家已确认收货)\n" +
            "STATE_REFUNDING（退款中) STATE_REFUND_SUCCESS（退款成功) STATE_UNUSED (未使用，虚拟商品有效) STATE_USED (已使用，虚拟商品有效) STATE_SUCCESS（交易成功) STATE_CLOSED(交易关闭)")
    private String status;
    @ApiModelProperty(value = "订单状态  交易状态 STATE_WAIT_BUYER_PAY（交易创建，等待买家付款）STATE_BUYER_PAYED (买家已付款) STATE_SELLER_DELIVERIED（卖家已发货，等待买家收货) STATE_BUYER_DELIVERIED（买家已确认收货)\n" +
            "STATE_REFUNDING（退款中) STATE_REFUND_SUCCESS（退款成功) STATE_UNUSED (未使用，虚拟商品有效) STATE_USED (已使用，虚拟商品有效) STATE_SUCCESS（交易成功) STATE_CLOSED(交易关闭)")
    private String statusStr;

    @ApiModelProperty(value = "收货人")
    private String receiverName;

    @ApiModelProperty(value = "收货人地址")
    private String receiveAddress;

    @ApiModelProperty(value = "收货人手机号")
    private String receiverMobile;

    @ApiModelProperty(value = "交易结束时间")
    private Date endTime;

    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;

    @ApiModelProperty(value = "订单关闭时间")
    private Date closeTime;

    @ApiModelProperty(value = "订单发货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "订单付款时间")
    private Date paymentTime;

    @ApiModelProperty(value = "订单更新时间")
    private Date updateTime;

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }


    public long getProductskuId() {
        return productskuId;
    }

    public void setProductskuId(long productskuId) {
        this.productskuId = productskuId;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(int actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private String updateField(String field){
        if(field == null){
            return "";
        }
        return field;
    }
}
