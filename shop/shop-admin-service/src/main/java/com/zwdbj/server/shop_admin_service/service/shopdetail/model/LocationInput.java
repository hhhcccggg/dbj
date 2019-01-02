package com.zwdbj.server.shop_admin_service.service.shopdetail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "位置信息")
public class LocationInput {

    @ApiModelProperty(value = "城市id")
    int cityId;
    @ApiModelProperty(value = "城市级别")
    String cityLevel;
    @ApiModelProperty(value = "详细地址")
    String address;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
