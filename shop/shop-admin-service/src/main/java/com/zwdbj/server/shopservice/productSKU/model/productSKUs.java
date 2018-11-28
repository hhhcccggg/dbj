package com.zwdbj.server.shopservice.productSKU.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "商品款式")
public class productSKUs {
    @ApiModelProperty(value = "sku编号")
    String skuNumber;
    @ApiModelProperty(value = "产品编号")
    long productId;
    @ApiModelProperty(value = "原价")
    float originalPrice;
    @ApiModelProperty(value = "促销价")
    float promotionPrice;
    @ApiModelProperty(value = "库存")
    long inventory;
    @ApiModelProperty(value = "销量")
    long sales;
    @ApiModelProperty(value = "规格属性列表")
    String attrs;
    @ApiModelProperty(value = "重量Kg")
    float weight;
    Date datecreateTime;
    boolean isDeleted;
    boolean isManualData;

}
