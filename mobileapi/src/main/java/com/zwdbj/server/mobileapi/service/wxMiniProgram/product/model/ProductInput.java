package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel(description = "小程序商品列表查询")
public class ProductInput {

    @ApiModelProperty(value = "排序:0默认 1 销量 2上架时间")
    int type;

    @Min(0)
    @ApiModelProperty(value = "店铺ID,如果为0这查询全部商品")
    long storeId;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
