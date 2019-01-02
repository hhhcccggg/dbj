package com.zwdbj.server.adminserver.service.userTenant.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "租户相关")
public class UserTenantInput implements Serializable {
    @ApiModelProperty(value = "租户的名字")
    @NotNull(message = "租户名字不能为空")
    private String name;
    @ApiModelProperty(value = "租户标识，要保持唯一性")
    @NotNull(message = "租户标识不能为空")
    private String identifyName;
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

    public String getIdentifyName() {
        return identifyName;
    }

    public void setIdentifyName(String identifyName) {
        this.identifyName = identifyName;
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
