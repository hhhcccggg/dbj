package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "登录模型")
public class UserPwdLoginInput {
    @ApiModelProperty(value = "账号:手机号")
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
