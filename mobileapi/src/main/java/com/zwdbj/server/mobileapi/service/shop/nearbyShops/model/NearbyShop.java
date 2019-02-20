package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(description = "附近商家信息")
public class NearbyShop implements Serializable {
    @ApiModelProperty(value = "店铺id")
    private long id;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "店铺商标")
    private String logoUrl;
    @ApiModelProperty(value = "综合评分")
    private int grade;
    @ApiModelProperty(value = "服务套餐")
    private List<ProductInfo> products;
    @ApiModelProperty(value = "服务范围")
    private List<StoreServiceCategory> serviceScopes;
    @ApiModelProperty(value = "经度")
    private float longitude;
    @ApiModelProperty(value = "纬度")
    private float latitude;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "城市")
    private int cityId;
    @ApiModelProperty(value = "cityLevel")
    private String cityLevel;

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    public List<StoreServiceCategory> getServiceScopes() {
        return serviceScopes;
    }

    public void setServiceScopes(List<StoreServiceCategory> serviceScopes) {
        this.serviceScopes = serviceScopes;
    }


}
