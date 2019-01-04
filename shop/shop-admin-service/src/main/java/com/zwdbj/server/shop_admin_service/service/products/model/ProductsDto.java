package com.zwdbj.server.shop_admin_service.service.products.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "商品信息")
public class ProductsDto {

    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "商品名称")
    String name;
    @ApiModelProperty(value = "库存")
    long inventory;
    @ApiModelProperty(value = "销量")
    long sales;
    @ApiModelProperty(value = "评论数")
    long commentCount;
    @ApiModelProperty(value = "创建时间")
    Date createTime;
    @ApiModelProperty(value = "单价")
    long originalPrice;
}
