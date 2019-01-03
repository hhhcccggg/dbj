package com.zwdbj.server.shop_admin_service.setting.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "服务范围")
public class ServiceScopes {
    @ApiModelProperty("服务范围id")
    long serviceScopesId;
    @ApiModelProperty(value = "服务范围")
    String serviceScope;

    public long getServiceScopesId() {
        return serviceScopesId;
    }

    public void setServiceScopesId(long serviceScopesId) {
        this.serviceScopesId = serviceScopesId;
    }

    public String getServiceScope() {
        return serviceScope;
    }

    public void setServiceScope(String serviceScope) {
        this.serviceScope = serviceScope;
    }
}
