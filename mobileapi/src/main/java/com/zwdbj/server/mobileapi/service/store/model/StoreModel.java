package com.zwdbj.server.mobileapi.service.store.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "店铺对象")
public class StoreModel {

    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("店铺编号")
    private String storeNumber;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty
    private String subName;
    @ApiModelProperty
    private String marketName;
    @ApiModelProperty
    private String shareDesc;
    @ApiModelProperty
    private String description;
    @ApiModelProperty
    private String logoUrl;
    @ApiModelProperty("推荐指数")
    private long recommendIndex;
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("维度")
    private String latitude;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("店铺类型>>SELF:自营THIRD:第三方入驻商家OFFLINE:线下门店")
    private String type;
    @ApiModelProperty("店铺等级")
    private int level;
    @ApiModelProperty("主营类目")
    private long categoryId;
    @ApiModelProperty
    private String categoryLevel;
    @ApiModelProperty
    private int cityId;
    @ApiModelProperty
    private String cityLevel;
    @ApiModelProperty("在平台开店的有效期")
    private Date expireTime;
    @ApiModelProperty
    private String contactName;
    @ApiModelProperty
    private String contactPhone;
    @ApiModelProperty
    private String qq;
    @ApiModelProperty("综合评分")
    private int grade;
    @ApiModelProperty("0：正常1：关闭")
    private int status;
    @ApiModelProperty("是否已审核通过")
    private boolean reviewed;
    @ApiModelProperty("是否停止服务")
    private boolean stopService;
    @ApiModelProperty("封面图")
    private String mainConverImage;
    @ApiModelProperty("封面图地址，多个用,隔开")
    private String coverImages;
    @ApiModelProperty("法律主体，也就是该店铺属于谁")
    private long legalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public long getRecommendIndex() {
        return recommendIndex;
    }

    public void setRecommendIndex(long recommendIndex) {
        this.recommendIndex = recommendIndex;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isStopService() {
        return stopService;
    }

    public void setStopService(boolean stopService) {
        this.stopService = stopService;
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

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}
