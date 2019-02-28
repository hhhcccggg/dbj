package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class StoreAuthenticationInfo implements Serializable {
    @ApiModelProperty(value = "店铺id")
    private long id;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "店铺的开店时间")
    private Date createTime;
    @ApiModelProperty(value = "店铺商标")
    private String logoUrl;
    @ApiModelProperty(value = "综合评分")
    private int grade;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
