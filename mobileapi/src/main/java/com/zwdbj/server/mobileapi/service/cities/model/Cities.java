package com.zwdbj.server.mobileapi.service.cities.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "城市")
public class Cities {

    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("邮编")
    private String zipcode;
    @ApiModelProperty("citycode")
    private String citycode;
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("维度")
    private String latitude;
    @ApiModelProperty("省市区")
    private LevelType level;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public LevelType getLevel() {
        return level;
    }

    public void setLevel(LevelType level) {
        this.level = level;
    }
}
