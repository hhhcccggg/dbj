package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "账号管理-修改密码")
public class AdModifyManagerPasswordInput {
    @ApiModelProperty(value = "原密码")
    String oldPassword;
    @ApiModelProperty(value = "新密码")
    String newPassword;
    @ApiModelProperty(value = "确认新密码")
    String mNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getmNewPassword() {
        return mNewPassword;
    }

    public void setmNewPassword(String mNewPassword) {
        this.mNewPassword = mNewPassword;
    }
}
