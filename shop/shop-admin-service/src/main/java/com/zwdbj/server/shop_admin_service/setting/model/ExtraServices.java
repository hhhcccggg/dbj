package com.zwdbj.server.shop_admin_service.setting.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "额外服务")
public class ExtraServices {
    @ApiModelProperty(value = "额外服务id")
    long extraServiceId;
    @ApiModelProperty(value = "额外服务内容")
    String extraService;

    public long getExtraServiceId() {
        return extraServiceId;
    }

    public void setExtraServiceId(long extraServiceId) {
        this.extraServiceId = extraServiceId;
    }

    public String getExtraService() {
        return extraService;
    }

    public void setExtraService(String extraService) {
        this.extraService = extraService;
    }
}
