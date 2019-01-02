package com.zwdbj.server.shop_admin_service.service.productBrands.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品品牌")
public class ProductBrands {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "品牌名称")
    String name;
    @ApiModelProperty(value = "图片url")
    String imageUrl;
    @ApiModelProperty(value = "品牌描述")
    String description;
    int orderIndex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
