package com.zwdbj.server.shop_admin_service.BusinessSellers.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "店铺的信息")
public class BusinessSellerModel {
    @ApiModelProperty(value = "店铺id")
    long id;
    @ApiModelProperty(value = "创建时间")
    Date createTime;
    @ApiModelProperty(value = "店铺编号")
    String sellerNumber;
    @ApiModelProperty(value = "店铺名称")
    String name;
    @ApiModelProperty(value = "副标题")
    String subName;
    @ApiModelProperty(value = "卖点的名称")
    String marketName;
    @ApiModelProperty(value = "分享的描述")
    String shareDesc;
    @ApiModelProperty(value = "店铺的描述")
    String description;
    @ApiModelProperty(value = "店铺的LOGO的URL")
    String logoUrl;
    @ApiModelProperty(value = "推荐指数")
    long recommendIndex;
    @ApiModelProperty(value = "经度")
    float longitude;
    @ApiModelProperty(value = "维度")
    float latitude;
    @ApiModelProperty(value = "店铺类型,1:自营商家2:第三方商家4:线下门店")
    int type;
    @ApiModelProperty(value = "店铺等级")
    int level;
    @ApiModelProperty(value = "店铺主营类目id")
    long categoryId;
    @ApiModelProperty(value = "类目的路径")
    String categoryLevel;
    @ApiModelProperty(value = "城市的id")
    int  cityId;
    @ApiModelProperty(value = "城市的路径")
    String cityLevel;
    @ApiModelProperty(value = "在平台开店的有效期")
    Date expireTime;
    @ApiModelProperty(value = "联系人姓名")
    String contactName;
    @ApiModelProperty(value = "联系人电话")
    String contactPhone;
    @ApiModelProperty(value = "联系人的QQ")
    String qq;
    @ApiModelProperty(value = "综合评分")
    float grade;
    @ApiModelProperty(value = "店铺的状态，0：正常，1：关闭")
    int status;
    @ApiModelProperty(value = "店铺是否通过审核")
    boolean isReviewed;
    @ApiModelProperty(value = "店铺是否停止服务")
    boolean isStopService;
    @ApiModelProperty(value = "封面主图url")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址，多个用,隔开")
    String coverImages;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isStopService() {
        return isStopService;
    }

    public void setStopService(boolean stopService) {
        isStopService = stopService;
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
}
