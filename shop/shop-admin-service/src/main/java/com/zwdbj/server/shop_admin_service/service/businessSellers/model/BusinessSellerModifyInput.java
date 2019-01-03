package com.zwdbj.server.shop_admin_service.service.businessSellers.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "修改店铺信息")
public class BusinessSellerModifyInput {
    @ApiModelProperty(value = "")
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
    @ApiModelProperty(value = "店铺是否通过审核")
    boolean isReviewed;
    @ApiModelProperty(value = "店铺是否停止服务")
    boolean isStopService;
    @ApiModelProperty(value = "封面主图url")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址，多个用,隔开")
    String coverImages;
    @ApiModelProperty(value = "联系人姓名")
    String contactName;
    @ApiModelProperty(value = "联系人电话")
    String contactPhone;
    @ApiModelProperty(value = "联系人的QQ")
    String qq;

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
}
