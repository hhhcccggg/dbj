package com.zwdbj.server.mobileapi.service.shop.shoppingcart.model;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "商品信息")
public class ProductInfo {

    private long count;
    private ProductSKU sku;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public ProductSKU getSku() {
        return sku;
    }

    public void setSku(ProductSKU sku) {
        this.sku = sku;
    }

    @Override
    public int hashCode() {
        //重新hashCode方法
        return super.hashCode();

    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProductInfo &&
                this.sku.getName().equals(((ProductInfo) obj).getSku().getName()) &&
                this.sku.getPrice() == (((ProductInfo) obj).getSku().getPrice());
    }

    public ProductInfo(ProductSKU sku, long count) {
        this.sku = sku;
        this.count = count;
    }

    public ProductInfo() {
    }
}
