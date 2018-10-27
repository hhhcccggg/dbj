package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "新建运营用户输入字段")
public class AdNewMarketInput {
    @ApiModelProperty(value = "用户姓名")
    String userName;
    @ApiModelProperty(value = "用户手机号")
    String phone;
    @ApiModelProperty(value = "性别,-1:女,0:未知,1:男")
    int gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
