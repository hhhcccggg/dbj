package com.zwdbj.server.mobileapi.service.shop.order.model;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "订单的简单model")
public class ProductOrderModel {
    @ApiModelProperty(value = "订单id")
    private long id;
    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;
    @ApiModelProperty(value = "商品的标题")
    private String title;
    @ApiModelProperty(value = "商品的单价 单位为分")
    private int price;
    @ApiModelProperty(value = "购买商品的数量")
    private int num;
    @ApiModelProperty(value = "订单的状态")
    private String status;
    @ApiModelProperty(value = "订单的状态中文说明")
    private String statusStr;
    @ApiModelProperty(value = "订单的收货地址id")
    private long receiveAddressId;
    @ApiModelProperty(value = "订单的收货地址")
    private String receiveAddress;
    @ApiModelProperty(value = "收货人")
    private String receiverName;
    @ApiModelProperty(value = "收货人手机号")
    private String receiverMobile;
    @ApiModelProperty(value = "此订单的实付金额,单位为分")
    private int actualPayment;
    @ApiModelProperty(value = "此订单使用的金币抵扣数")
    private int useCoin;
    @ApiModelProperty(value = "订单总金额，单位为分")
    private int payment;
    @ApiModelProperty(value = "用户id")
    private long userId;
    @ApiModelProperty(value = "商品id")
    private long productId;
    @ApiModelProperty(value = "商品skuid")
    private long productskuId;
    @ApiModelProperty(value = "用户的昵称")
    private String nickName;
    @ApiModelProperty(value = "用户使用的优惠券id,若为空或为0,则表示没用优惠券")
    private String couponids;
    @ApiModelProperty(value = "买家是否已评价")
    private boolean buyerRate;
    @ApiModelProperty(value = "店铺的id")
    private long storeId;
    @ApiModelProperty(value = "店铺的名字")
    private String storeName;
    @ApiModelProperty(value = "店铺的第一张封面图")
    private String mainConverImage;
    @ApiModelProperty(value = "商品的图片地址")
    private String imageUrls;

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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getMainConverImage() {
        return mainConverImage;
    }

    public void setMainConverImage(String mainConverImage) {
        this.mainConverImage = mainConverImage;
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

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public boolean isBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    public String getCouponids() {
        return couponids;
    }

    public void setCouponids(String couponids) {
        this.couponids = couponids;
    }

    public long getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(long receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getUseCoin() {
        return useCoin;
    }

    public void setUseCoin(int useCoin) {
        this.useCoin = useCoin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(int actualPayment) {
        this.actualPayment = actualPayment;
    }
}
