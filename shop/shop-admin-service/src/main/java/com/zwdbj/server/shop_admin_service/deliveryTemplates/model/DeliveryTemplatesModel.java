package com.zwdbj.server.shop_admin_service.deliveryTemplates.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "物流相关")
public class DeliveryTemplatesModel {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "卖家ID")
    private long sellerId;
    @ApiModelProperty(value = "商品的名字")
    private String name;
    @ApiModelProperty(value = "商品的计费类型")
    private int billType;
    @ApiModelProperty(value = "配送范围")
    private long deliveryScopeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public long getDeliveryScopeId() {
        return deliveryScopeId;
    }

    public void setDeliveryScopeId(long deliveryScopeId) {
        this.deliveryScopeId = deliveryScopeId;
    }
}
