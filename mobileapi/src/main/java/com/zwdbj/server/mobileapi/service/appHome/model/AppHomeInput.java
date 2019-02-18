package com.zwdbj.server.mobileapi.service.appHome.model;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "APP首页相关")
public class AppHomeInput implements Serializable {
    @ApiModelProperty(value = "城市id")
    private int citrId;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "经度")
    private float longitude;
    @ApiModelProperty(value = "纬度")
    private float latitude;
    @ApiModelProperty(value = "运营活动的banner输入字段")
    private AdBannerInput adBannerInput1;
    @ApiModelProperty(value = "金币任务的banner输入字段")
    private AdBannerInput adBannerInput2;
    @ApiModelProperty(value = "优惠折扣的banner输入字段")
    private AdBannerInput adBannerInput3;

    public int getCitrId() {
        return citrId;
    }

    public void setCitrId(int citrId) {
        this.citrId = citrId;
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

    public AdBannerInput getAdBannerInput1() {
        return adBannerInput1;
    }

    public void setAdBannerInput1(AdBannerInput adBannerInput1) {
        this.adBannerInput1 = adBannerInput1;
    }

    public AdBannerInput getAdBannerInput2() {
        return adBannerInput2;
    }

    public void setAdBannerInput2(AdBannerInput adBannerInput2) {
        this.adBannerInput2 = adBannerInput2;
    }

    public AdBannerInput getAdBannerInput3() {
        return adBannerInput3;
    }

    public void setAdBannerInput3(AdBannerInput adBannerInput3) {
        this.adBannerInput3 = adBannerInput3;
    }
}
