package com.zwdbj.server.shop_admin_service.service.productAttriValues.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品属性规格值")
public class ProductAttriValues {
    @ApiModelProperty(value = "id")
    Long id;
    /// <summary>
    /// 商品属性规格值
    @ApiModelProperty(value = "属性规格值")
    String attiValue;
    @ApiModelProperty(value = "")
    Long productAttriId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttiValue() {
        return attiValue;
    }

    public void setAttiValue(String attiValue) {
        this.attiValue = attiValue;
    }

    public Long getProductAttriId() {
        return productAttriId;
    }

    public void setProductAttriId(Long productAttriId) {
        this.productAttriId = productAttriId;
    }
}



