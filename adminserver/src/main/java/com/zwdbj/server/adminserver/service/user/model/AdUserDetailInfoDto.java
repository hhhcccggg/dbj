package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "增加了角色权限名")
public class AdUserDetailInfoDto extends UserDetailInfoDto {
    @ApiModelProperty(value = "角色权限")
    String powerName;

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }
}
