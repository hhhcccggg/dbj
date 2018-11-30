package com.zwdbj.server.shop_admin_service.BusinessSellers.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "添加新店铺需要的字段")
public class BusinessSellerAddInput {
    @ApiModelProperty(value = "店铺名字",required = true)
    String name;
    @ApiModelProperty(required = true)
    String address;
    @ApiModelProperty(value = "店铺类型,1:自营商家2:第三方商家4:线下门店",required = true)
    int type;
    @ApiModelProperty(required = true)
    long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
