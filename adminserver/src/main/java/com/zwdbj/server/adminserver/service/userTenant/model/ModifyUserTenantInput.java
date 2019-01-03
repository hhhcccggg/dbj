package com.zwdbj.server.adminserver.service.userTenant.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

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
    @ApiModelProperty(value = "租户座机号码")
    private String contactNumber;

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
