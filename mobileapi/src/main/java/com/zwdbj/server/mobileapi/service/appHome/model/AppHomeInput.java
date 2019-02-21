package com.zwdbj.server.mobileapi.service.appHome.model;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import com.zwdbj.server.mobileapi.service.adBanner.moder.Platform;
import com.zwdbj.server.mobileapi.service.adBanner.moder.Type;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "APP首页相关")
public class AppHomeInput implements Serializable {
    @ApiModelProperty(value = "平台类型 IOS:苹果ANDROID:安卓ALL:所有")
    private String platform;
    @ApiModelProperty(value = "城市id")
    private int cityId;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "经度")
    private float longitude;
    @ApiModelProperty(value = "纬度")
    private float latitude;


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

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
