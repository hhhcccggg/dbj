package com.zwdbj.server.adminserver.service.shop.service.productOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "订单的简单model")
public class ProductOrderModel {
    @ApiModelProperty(value = "订单id")
    long id;
    @ApiModelProperty(value = "订单创建时间")
    Date createTime;




}
