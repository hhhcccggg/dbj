package com.zwdbj.server.adminserver.service.shop.service.legalSubject.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ShopTenantModel {
    @ApiModelProperty(value = "租户法人姓名")
    private String leagalRepresentativeName;
    @ApiModelProperty(value = "租户法人身份证号码")
    private String leagalRepresentativeID;
    @ApiModelProperty(value = "租户所在的城市id")
    private int cityId;
    @ApiModelProperty(value = "租户的类型：P:个人；B:企业")
    private String legalType;
    @ApiModelProperty(value = "租户座机号码")
    private String contactNumber;
    @ApiModelProperty(value = "租户店铺所主营类目id")
    private long categoryId;
    @ApiModelProperty(value = "租户的店铺类型>>SELF:自营THIRD:第三方入驻商家OFFLINE:线下门店")
    private String storeType;
    @ApiModelProperty(value = "在平台开店的有效期")
    private Date expireTime;
    @ApiModelProperty(value = "商家的联系人")
    private String contactName;
    @ApiModelProperty(value = "商家联系人的手机号码")
    private String contactPhone;

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

    public String getLeagalRepresentativeName() {
        return leagalRepresentativeName;
    }

    public void setLeagalRepresentativeName(String leagalRepresentativeName) {
        this.leagalRepresentativeName = leagalRepresentativeName;
    }

    public String getLeagalRepresentativeID() {
        return leagalRepresentativeID;
    }

    public void setLeagalRepresentativeID(String leagalRepresentativeID) {
        this.leagalRepresentativeID = leagalRepresentativeID;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getLegalType() {
        return legalType;
    }

    public void setLegalType(String legalType) {
        this.legalType = legalType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
