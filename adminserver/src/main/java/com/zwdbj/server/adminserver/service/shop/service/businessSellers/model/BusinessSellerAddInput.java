package com.zwdbj.server.adminserver.service.shop.service.businessSellers.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "添加新店铺需要的字段")
public class BusinessSellerAddInput {
    @ApiModelProperty(value = "店铺名字")
    String name;
    @ApiModelProperty(value = "店铺地址")
    String address;
    @ApiModelProperty(value = "店铺类型,1:自营商家2:第三方商家4:线下门店")
    int type;
    @ApiModelProperty(value = "店铺的分类")
    long categoryId;
    @ApiModelProperty(value = "联系人")
    String contactName;
    @ApiModelProperty(value = "联系人的手机号码")
    String contactPhone;
    @ApiModelProperty("城市id")
    int cityId;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
