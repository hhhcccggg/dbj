package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModelProperty;

public class ShopUserLoginInput {
    @ApiModelProperty(value = "手机号码")
    String phone;
    @ApiModelProperty(value = "密码")
    String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
