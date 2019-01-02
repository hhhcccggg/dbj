package com.zwdbj.server.shop_admin_service.service.shopdetail.model;

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


}
