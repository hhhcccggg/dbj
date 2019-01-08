package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "店铺信息")
public class ShopInfo {
    @ApiModelProperty(value = "店铺id")
    long id;
    @ApiModelProperty(value = "name")
    String name;
    @ApiModelProperty(value = "店铺商标")
    String logoUrl;
    @ApiModelProperty(value = "综合评分")
    int grade;
    @ApiModelProperty(value = "subName")
    String subName;
    @ApiModelProperty(value = "地址")
    String address;
    @ApiModelProperty(value = "优惠券")
    List<DiscountCoupon> discountCoupons;
    @ApiModelProperty(value = "服务范围")
    List<ShopServices> serviceScopes;
    @ApiModelProperty(value = "额外服务范围")
    List<ShopServices> extraServices;
    @ApiModelProperty(value = "营业时间")
    List<OpeningHours> openingHours;
    @ApiModelProperty(value = "封面图")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    String coverImages;

    public List<ShopServices> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(List<ShopServices> extraServices) {
        this.extraServices = extraServices;
    }

    public List<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public String getMainConverImage() {
        return mainConverImage;
    }

    public void setMainConverImage(String mainConverImage) {
        this.mainConverImage = mainConverImage;
    }

    public String getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(String coverImages) {
        this.coverImages = coverImages;
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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DiscountCoupon> getDiscountCoupons() {
        return discountCoupons;
    }

    public void setDiscountCoupons(List<DiscountCoupon> discountCoupons) {
        this.discountCoupons = discountCoupons;
    }

    public List<ShopServices> getServiceScopes() {
        return serviceScopes;
    }

    public void setServiceScopes(List<ShopServices> serviceScopes) {
        this.serviceScopes = serviceScopes;
    }
}
