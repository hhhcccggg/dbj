package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "创建用户")
public class CreateUserInput {
    @ApiModelProperty(value = "账号/用户编码/类似抖音称谓，必须全局唯一")
    String userName;
    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "姓名")
    String name;
    @ApiModelProperty(value = "可以为用户指定多个角色")
    String[] roleName;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoleName() {
        return roleName;
    }

    public void setRoleName(String[] roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
