package com.zwdbj.server.mobileapi.service.shop.shoppingcart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "购物车")
public class ShoppingCart {
    @ApiModelProperty(value = "商品明细")
    private List<ProductInfo> items = new ArrayList<>();

    public List<ProductInfo> getItems() {
        return items;
    }

    public void setItems(List<ProductInfo> items) {
        this.items = items;
    }
}
