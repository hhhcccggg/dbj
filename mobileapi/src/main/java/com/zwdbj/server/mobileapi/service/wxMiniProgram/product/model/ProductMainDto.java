package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "商品基本信息")
public class ProductMainDto implements Serializable {

    private Long id;

    @ApiModelProperty(value = "商品类型")
    private Long productType;

    @ApiModelProperty(value = "产品详细类型")
    private String productDetailType;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品图片地址")
    private String imageUrls;

    @ApiModelProperty(value = "productSKUId")
    private long productSKUId;

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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public long getProductSKUId() {
        return productSKUId;
    }

    public void setProductSKUId(long productSKUId) {
        this.productSKUId = productSKUId;
    }
}
