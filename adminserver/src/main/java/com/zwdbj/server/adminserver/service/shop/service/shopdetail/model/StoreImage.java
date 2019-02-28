package com.zwdbj.server.adminserver.service.shop.service.shopdetail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "店铺照片")
public class StoreImage {
    @ApiModelProperty(value = "照片url")
    private String imageUrl;
    @ApiModelProperty(value = "照片类型 logoUrl 商标 mainConverImage 门面图 coverImages环境照片（可能有多张环境照片，拼接字符串）")
    private String type;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
