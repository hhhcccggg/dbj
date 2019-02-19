package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "附近店铺商品信息")
public class StoreProduct {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "商家id")
    Long storeId;
    @ApiModelProperty(value = "商品类型")
    Long productType;

    @ApiModelProperty(value = "产品详细类型")
    String productDetailType;

    @ApiModelProperty(value = "商品名称")
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
