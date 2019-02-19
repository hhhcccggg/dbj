package com.zwdbj.server.mobileapi.service.appHome.model;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "APP首页相关")
public class AppHomeInput implements Serializable {
    @ApiModelProperty(value = "城市id")
    private int cityId;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "经度")
    private float longitude;
    @ApiModelProperty(value = "纬度")
    private float latitude;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

}
