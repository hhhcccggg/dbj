package com.zwdbj.server.adminserver.service.userTenant.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ModifyUserTenantInput {
    @ApiModelProperty(value = "租户的名字")
    @NotNull(message = "租户名字不能为空")
    private String name;
    @ApiModelProperty(value = "租户的联系人")
    @NotNull(message = "联系人不能为空")
    private String username;
    @ApiModelProperty(value = "租户联系人的手机号码")
    @NotNull(message = "手机号码不能为空")
    private String phone;
    @ApiModelProperty(value = "租户法人姓名")
    @NotNull(message = "法人姓名不能为空")
    private String leagalRepresentativeName;
    @ApiModelProperty(value = "租户法人身份证号码")
    @NotNull(message = "法人身份证号码不能为空")
    private String leagalRepresentativeID;
    @ApiModelProperty(value = "租户所在的城市id")
    private int cityId;
    @ApiModelProperty(value = "租户的类型：P:个人；B:企业")
    @NotNull(message = "租户的类型不能为空")
    private String legalType;
    @ApiModelProperty(value = "租户座机号码")
    private String contactNumber;
    @ApiModelProperty(value = "租户店铺所主营类目id")
    private long categoryId;
    @ApiModelProperty(value = "租户的店铺类型>>SELF:自营THIRD:第三方入驻商家OFFLINE:线下门店")
    @NotNull(message = "租户的店铺类型不能为空")
    private String storeType;
    @ApiModelProperty(value = "在平台开店的有效期")
    @NotNull(message = "租户的店铺有效期不能为空")
    private Date expireTime;


    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
