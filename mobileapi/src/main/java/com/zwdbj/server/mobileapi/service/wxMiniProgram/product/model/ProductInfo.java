package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商家主页商品，优惠券信息")
public class ProductInfo {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "商家id")
    Long storeId;
    @ApiModelProperty(value = "商品类型O:实物产品1:虚拟商品")
    Long productType;

    @ApiModelProperty(value = "产品详细类型 DELIVERY: 实物产品 NODELIVERY:虚拟商品(不需要物流) " +
            "CARD:卡券(服务中套餐)，关联[ProductCard]表 CASHCOUPON:代金券，类似70抵100，关联[ProductCashCoupon]表")
    String productDetailType;

    @ApiModelProperty(value = "商品名称")
    String name;
    @ApiModelProperty(value = "库存")
    long inventory;

    @ApiModelProperty(value = "销量")
    long sales;
    @ApiModelProperty(value = "原价")
    long originalPrice;

    @ApiModelProperty(value = "促销价")
    Long promotionPrice;
    @ApiModelProperty(value = "是否限购 0：表示不限购 大于0数字表示没人只能买商品的数量")
    int limitPerPerson;

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public int getLimitPerPerson() {
        return limitPerPerson;
    }

    public void setLimitPerPerson(int limitPerPerson) {
        this.limitPerPerson = limitPerPerson;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public String getProductDetailType() {
        return productDetailType;
    }

    public void setProductDetailType(String productDetailType) {
        this.productDetailType = productDetailType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
