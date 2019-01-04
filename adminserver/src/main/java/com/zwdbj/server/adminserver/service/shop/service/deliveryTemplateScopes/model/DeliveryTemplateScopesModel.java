package com.zwdbj.server.adminserver.service.shop.service.deliveryTemplateScopes.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "物流配送区域")
public class DeliveryTemplateScopesModel {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "配送范围")
    private String deliveryArea;
    @ApiModelProperty(value = "配送项")
    private int item;
    @ApiModelProperty(value = "配送项价格")
    private float itemPrice;

    private int overItem;

    private float overItemPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getOverItem() {
        return overItem;
    }

    public void setOverItem(int overItem) {
        this.overItem = overItem;
    }

    public float getOverItemPrice() {
        return overItemPrice;
    }

    public void setOverItemPrice(float overItemPrice) {
        this.overItemPrice = overItemPrice;
    }
}
