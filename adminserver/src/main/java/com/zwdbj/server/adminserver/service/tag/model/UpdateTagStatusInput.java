package com.zwdbj.server.adminserver.service.tag.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "更改主题的状态")
public class UpdateTagStatusInput {
    @ApiModelProperty(value = "更改视频主题的状态0:正常,1:停用")
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
