package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "新建管理账号输入字段")
public class AdNewManageUserInput {
    @ApiModelProperty(value = "姓名")
    String userName;
    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "角色权限 admin,finance,market")
    String roleName;

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
