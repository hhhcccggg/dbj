package com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品款式")
public class ProductSKUs {
    @ApiModelProperty(value = "id")
    long id;
    @ApiModelProperty(value = "sku编号")
    String skuNumber;
    @ApiModelProperty(value = "产品编号")
    long productId;
    @ApiModelProperty(value = "原价")
    long originalPrice;
    @ApiModelProperty(value = "促销价")
    long promotionPrice;
    @ApiModelProperty(value = "库存")
    long inventory;
    @ApiModelProperty(value = "销量")
    long sales;
    @ApiModelProperty(value = "规格属性列表")
    String attrs;
    @ApiModelProperty(value = "重量Kg")
    float weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

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

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
