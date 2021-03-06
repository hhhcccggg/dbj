package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.StoreProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "搜索商家信息")
public class SearchShop {
    @ApiModelProperty(value = "店铺id")
    private long id;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "店铺商标")
    private String logoUrl;
    @ApiModelProperty(value = "综合评分")
    private int grade;
    @ApiModelProperty(value = "商家商品")
    private List<StoreProduct> storeProducts;
    @ApiModelProperty(value = "服务范围")
    private List<StoreServiceCategory> serviceScopes;

    @ApiModelProperty(value = "纬度,经度")
    private String location;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "城市")
    private int cityId;
    @ApiModelProperty(value = "cityLevel")
    private String cityLevel;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<StoreProduct> getStoreProducts() {
        return storeProducts;
    }

    public void setStoreProducts(List<StoreProduct> storeProducts) {
        this.storeProducts = storeProducts;
    }

    public List<StoreServiceCategory> getServiceScopes() {
        return serviceScopes;
    }

    public void setServiceScopes(List<StoreServiceCategory> serviceScopes) {
        this.serviceScopes = serviceScopes;
    }


}
