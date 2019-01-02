package com.zwdbj.server.shop_admin_service.setting.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "商铺基本信息")
public class BasicMsg {
    @ApiModelProperty(value = "店铺名称")
    String shopName;
    @ApiModelProperty(value = "店长姓名")
    String sellerName;
    @ApiModelProperty(value = "店主手机号")
    String sellerPhone;
    @ApiModelProperty(value = "联系电话")
    String contactPhone;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
