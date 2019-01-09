package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "店铺详情信息")
public class ShopInfo extends NearbyShop implements Serializable {


    private static final long serialVersionUID = 3991973629366868716L;

    @ApiModelProperty(value = "subName")
    String subName;
    @ApiModelProperty(value = "额外服务范围")
    List<StoreServiceCategory> extraServices;
    @ApiModelProperty(value = "营业时间")
    List<OpeningHours> openingHours;
    @ApiModelProperty(value = "封面图")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    String coverImages;
    @ApiModelProperty(value = "商家id")
    long legalSubjectId;

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
