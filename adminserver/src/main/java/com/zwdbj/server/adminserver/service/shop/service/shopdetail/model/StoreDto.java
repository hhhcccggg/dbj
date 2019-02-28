package com.zwdbj.server.adminserver.service.shop.service.shopdetail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "店铺基本信息")
public class StoreDto {
    @ApiModelProperty(value = "店铺id")
    long id;
    @ApiModelProperty(value = "店铺名称")
    String name;
    @ApiModelProperty(value = "店主姓名")
    String contactName;
    @ApiModelProperty(value = "店主手机号")
    String contactPhone;
    @ApiModelProperty(value = "联系电话")
    String phone;
    @ApiModelProperty(value = "审核结果 通过，未通过")
    boolean reviewed;
    @ApiModelProperty(value = "审核状态 0：正常1：关闭,2审核中")
    int status;
    @ApiModelProperty(value = "审核结果原因")
    String rejectMsg;
    @ApiModelProperty(value = "店铺商标")
    String logoUrl;
    @ApiModelProperty(value = "封面图")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    String coverImages;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }
}
