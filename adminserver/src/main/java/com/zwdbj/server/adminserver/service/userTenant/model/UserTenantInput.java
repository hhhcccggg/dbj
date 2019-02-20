package com.zwdbj.server.adminserver.service.userTenant.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(description = "租户相关")
public class UserTenantInput extends ModifyUserTenantInput implements Serializable {
    @ApiModelProperty(value = "租户标识，要保持唯一性")
    private String identifyName;

    public String getIdentifyName() {
        return identifyName;
    }

    public void setIdentifyName(String identifyName) {
        this.identifyName = identifyName;
    }

}
