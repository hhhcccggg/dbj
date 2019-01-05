package com.zwdbj.server.adminserver.service.shop.service.productAttriLinks.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "产品属性关系表")
public class ProductAttriLinks {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "商品id")
    Long productId;
    @ApiModelProperty(value = "属性id")
    Long attriId;
    @ApiModelProperty(value = "属性值id")
    Long attriValueId;
    @ApiModelProperty(value = "额外的数据存储")
    String extraData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAttriId() {
        return attriId;
    }

    public void setAttriId(Long attriId) {
        this.attriId = attriId;
    }

    public Long getAttriValueId() {
        return attriValueId;
    }

    public void setAttriValueId(Long attriValueId) {
        this.attriValueId = attriValueId;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
