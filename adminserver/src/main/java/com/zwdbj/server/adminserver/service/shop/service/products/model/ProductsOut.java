package com.zwdbj.server.adminserver.service.shop.service.products.model;

import com.zwdbj.server.adminserver.service.shop.service.productCard.model.ProductCard;
import com.zwdbj.server.adminserver.service.shop.service.productCashCoupon.model.ProductCashCoupon;
import com.zwdbj.server.adminserver.service.shop.service.productSKUs.model.ProductSKUs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "单个对象数据")
public class ProductsOut {

    @ApiModelProperty
    Products products;
    @ApiModelProperty
    ProductSKUs productSKUs;
    @ApiModelProperty
    ProductCard productCard;
    @ApiModelProperty
    ProductCashCoupon productCashCoupon;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public ProductSKUs getProductSKUs() {
        return productSKUs;
    }

    public void setProductSKUs(ProductSKUs productSKUs) {
        this.productSKUs = productSKUs;
    }

    public ProductCard getProductCard() {
        return productCard;
    }

    public void setProductCard(ProductCard productCard) {
        this.productCard = productCard;
    }

    public ProductCashCoupon getProductCashCoupon() {
        return productCashCoupon;
    }

    public void setProductCashCoupon(ProductCashCoupon productCashCoupon) {
        this.productCashCoupon = productCashCoupon;
    }
}
