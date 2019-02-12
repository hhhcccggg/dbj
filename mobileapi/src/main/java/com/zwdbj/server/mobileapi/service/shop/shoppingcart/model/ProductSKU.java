package com.zwdbj.server.mobileapi.service.shop.shoppingcart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品规格信息")
public class ProductSKU {
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "商品价格")
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProductSKU &&
                this.getName().equals(((ProductSKU) obj).getName()) &&
                this.getPrice() == ((ProductSKU) obj).getPrice();
    }
}
