package com.zwdbj.server.adminserver.service.shop.service.store.model;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.DiscountCoupon;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "店铺详细信息")
public class StoreInfo {
    @ApiModelProperty(value = "店铺id")
    long id;
    @ApiModelProperty(value = "店铺名称")
    String name;
    @ApiModelProperty(value = "店主姓名")
    String contactName;
    @ApiModelProperty(value = "店主手机号 同联系人电话")
    String conntactPhone;
    @ApiModelProperty(value = "营业时间")
    List<OfflineStoreOpeningHours> openingHours;
    @ApiModelProperty(value = "服务范围")
    List<OfflineStoreServiceScopes> serviceScopes;
    @ApiModelProperty(value = "附近服务")
    List<OfflineStoreExtraServices> extraServices;
    @ApiModelProperty(value = "纬度")
    double latitude;
    @ApiModelProperty(value = "经度")
    double longitude;
    @ApiModelProperty(value = "纬度，经度")
    String location;
    @ApiModelProperty(value = "详细地址")
    String address;
    @ApiModelProperty(value = "综合评分")
    int grade;
    @ApiModelProperty(value = "营业状态 0 正常1关闭")
    int status;
    @ApiModelProperty(value = "封面图")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    String coverImages;
    @ApiModelProperty(value = "头像")
    String logoUrl;
    @ApiModelProperty(value = "优惠券")
    List<DiscountCoupon> discountCoupons;
    @ApiModelProperty(value = "城市")
    int cityId;
    @ApiModelProperty(value = "cityLevel")
    String cityLevel;

    public List<DiscountCoupon> getDiscountCoupons() {
        return discountCoupons;
    }

    public void setDiscountCoupons(List<DiscountCoupon> discountCoupons) {
        this.discountCoupons = discountCoupons;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getConntactPhone() {
        return conntactPhone;
    }

    public void setConntactPhone(String conntactPhone) {
        this.conntactPhone = conntactPhone;
    }

    public List<OfflineStoreOpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OfflineStoreOpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public List<OfflineStoreServiceScopes> getServiceScopes() {
        return serviceScopes;
    }

    public void setServiceScopes(List<OfflineStoreServiceScopes> serviceScopes) {
        this.serviceScopes = serviceScopes;
    }

    public List<OfflineStoreExtraServices> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(List<OfflineStoreExtraServices> extraServices) {
        this.extraServices = extraServices;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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
}
