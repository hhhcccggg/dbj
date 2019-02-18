package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "店铺详情信息")
public class ShopInfo extends NearbyShop implements Serializable {


    private static final long serialVersionUID = 3991973629366868716L;

    @ApiModelProperty(value = "副标题/名字")
    private String subName;
    @ApiModelProperty(value = "额外服务范围")
    private List<StoreServiceCategory> extraServices;
    @ApiModelProperty(value = "营业时间")
    private List<OpeningHours> openingHours;
    @ApiModelProperty(value = "封面图")
    private String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    private String coverImages;
    @ApiModelProperty(value = "商家id")
    private long legalSubjectId;
    /**
     * 当前用户是否已经收藏
     */
    @ApiModelProperty(value = "当前用户时候已经收藏此商家，如果是匿名用户，始终返回false")
    private boolean favorited;
    /**
     * 是否资质认证
     */
    @ApiModelProperty(value = "当前商家是否资质认证")
    private boolean verify;
    @ApiModelProperty(value = "联系人")
    private String contactName;
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
    @ApiModelProperty(value = "开店时间，终端可用于直接显示")
    private String openingHoursDisplay;
    @ApiModelProperty(value = "其他服务，终端可用于直接显示")
    private String extraServicesDisplay;
    @ApiModelProperty(value = "服务范围，终端可用于直接显示")
    private String serviceScopesDisplay;

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
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

    public String getOpeningHoursDisplay() {
        return openingHoursDisplay;
    }

    public void setOpeningHoursDisplay(String openingHoursDisplay) {
        this.openingHoursDisplay = openingHoursDisplay;
    }

    public String getExtraServicesDisplay() {
        return extraServicesDisplay;
    }

    public void setExtraServicesDisplay(String extraServicesDisplay) {
        this.extraServicesDisplay = extraServicesDisplay;
    }

    public String getServiceScopesDisplay() {
        return serviceScopesDisplay;
    }

    public void setServiceScopesDisplay(String serviceScopesDisplay) {
        this.serviceScopesDisplay = serviceScopesDisplay;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public List<StoreServiceCategory> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(List<StoreServiceCategory> extraServices) {
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

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}
