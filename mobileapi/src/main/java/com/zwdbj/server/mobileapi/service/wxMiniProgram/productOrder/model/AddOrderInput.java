package com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "生成订单所需字段")
public class AddOrderInput {

    @NotNull(message = "使用的小饼干抵扣数不能为空")
    @ApiModelProperty(value = "使用的小饼干抵扣数")
    private int useCoin;

    @NotNull(message = "邮费不能为空")
    @ApiModelProperty(value = "邮费，单位分")
    private int deliveryFee;

    @ApiModelProperty(value = "买家留言")
    private String buyerComment;

    @NotNull(message = "店铺id不能为空")
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    @NotNull(message = "收货地址不能为空")
    @ApiModelProperty(value = "收货地址id")
    private long receiveAddressId;
    @NotNull(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id")
    private long productId;
    @NotNull(message = "商品SKUid不能为空")
    @ApiModelProperty(value = "商品SKUid")
    private long productskuId;
    @NotNull(message = "购买的商品数量不能为空")
    @ApiModelProperty(value = "购买的商品数量")
    private int num;
    @NotNull(message = "商品单价不能为空")
    @ApiModelProperty(value = "购买的商品单价（小饼干）")
    private int price_coin;
    @NotNull(message = "商品标题不能为空")
    @ApiModelProperty(value = "商品标题")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getPrice_coin() {
        return price_coin;
    }

    public void setPrice_coin(int price_coin) {
        this.price_coin = price_coin;
    }
}
