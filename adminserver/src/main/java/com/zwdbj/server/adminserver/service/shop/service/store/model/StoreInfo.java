package com.zwdbj.server.adminserver.service.shop.service.store.model;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model.OfflineStoreExtraServices;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreOpeningHour.model.OfflineStoreOpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import com.zwdbj.server.adminserver.service.shop.service.storeReview.model.BusinessSellerReviewModel;
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
    String contactPhone;
    @ApiModelProperty(value = "优惠券")
    List<DiscountCouponModel> discountCoupons;
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
    @ApiModelProperty(value = "店铺状态 0：正常1：关闭2:审核中")
    int status;
    @ApiModelProperty(value = "封面图")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    String coverImages;
    @ApiModelProperty(value = "logo")
    String logoUrl;

    @ApiModelProperty(value = "城市")
    int cityId;
    @ApiModelProperty(value = "cityLevel")
    String cityLevel;
    @ApiModelProperty(value = "店铺营业状态,是否停止服务")
    private boolean stopService;
    @ApiModelProperty(value = "是否通过审核")
    private boolean reviewed;
    @ApiModelProperty(value = "店铺类型  SELF:自营THIRD:第三方入驻商家OFFLINE:线下门店")
    private String type;
    @ApiModelProperty(value = "店铺qq")
    private String qq;
    @ApiModelProperty(value = "在平台开店的有效期")
    private String expireTime;
    @ApiModelProperty(value = "店铺等级")
    private int level;
    @ApiModelProperty(value = "商家主体id")
    private long legalSubjectId;
    @ApiModelProperty(value = "主营类目")
    private long categoryId;
    @ApiModelProperty(value = "店铺资质的图片")
    private List<BusinessSellerReviewModel> businessSellerReviewModels;

    public List<BusinessSellerReviewModel> getBusinessSellerReviewModels() {
        return businessSellerReviewModels;
    }

    public void setBusinessSellerReviewModels(List<BusinessSellerReviewModel> businessSellerReviewModels) {
        this.businessSellerReviewModels = businessSellerReviewModels;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStopService() {
        return stopService;
    }

    public void setStopService(boolean stopService) {
        this.stopService = stopService;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public List<DiscountCouponModel> getDiscountCoupons() {
        return discountCoupons;
    }

    public void setDiscountCoupons(List<DiscountCouponModel> discountCoupons) {
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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
